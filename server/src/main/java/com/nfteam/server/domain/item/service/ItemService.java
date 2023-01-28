package com.nfteam.server.domain.item.service;

import com.nfteam.server.domain.coin.entity.Coin;
import com.nfteam.server.domain.item.utils.CredentialEncryptUtils;
import com.nfteam.server.dto.request.item.ItemCreateRequest;
import com.nfteam.server.dto.request.item.ItemSellRequest;
import com.nfteam.server.dto.response.item.ItemPriceHistoryResponse;
import com.nfteam.server.dto.response.item.ItemResponse;
import com.nfteam.server.dto.response.item.ItemTradeHistoryResponse;
import com.nfteam.server.exception.auth.NotAuthorizedException;
import com.nfteam.server.exception.item.ItemCollectionNotFoundException;
import com.nfteam.server.exception.item.ItemCreateRequestNotValidException;
import com.nfteam.server.exception.item.ItemNotFoundException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.domain.item.entity.Item;
import com.nfteam.server.domain.item.entity.ItemCollection;
import com.nfteam.server.domain.item.entity.ItemCredential;
import com.nfteam.server.domain.item.repository.ItemCollectionRepository;
import com.nfteam.server.domain.item.repository.ItemCredentialRepository;
import com.nfteam.server.domain.item.repository.ItemRepository;
import com.nfteam.server.domain.member.entity.Member;
import com.nfteam.server.domain.member.repository.MemberRepository;
import com.nfteam.server.security.userdetails.MemberDetails;
import com.nfteam.server.domain.transaction.repository.QTransActionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCredentialRepository itemCredentialRepository;
    private final ItemCollectionRepository collectionRepository;
    private final MemberRepository memberRepository;

    private final QTransActionRepository qTransActionRepository;
    private final CredentialEncryptUtils credentialEncryptUtils;

    public ItemService(ItemRepository itemRepository,
                       ItemCredentialRepository itemCredentialRepository,
                       ItemCollectionRepository collectionRepository,
                       MemberRepository memberRepository,
                       QTransActionRepository qTransActionRepository,
                       CredentialEncryptUtils credentialEncryptUtils) {
        this.itemRepository = itemRepository;
        this.itemCredentialRepository = itemCredentialRepository;
        this.collectionRepository = collectionRepository;
        this.memberRepository = memberRepository;
        this.qTransActionRepository = qTransActionRepository;
        this.credentialEncryptUtils = credentialEncryptUtils;
    }

    @Transactional
    public Long save(ItemCreateRequest itemCreateRequest, MemberDetails memberDetails) throws Exception {
        Item item = itemCreateRequest.toItem();

        // 소유자 지정
        Member member = getMemberByEmail(memberDetails.getEmail());
        item.assignMember(member);

        // 컬렉션 지정
        ItemCollection itemCollection = getItemCollectionById(itemCreateRequest.getItemCollectionId());
        item.assignCollection(itemCollection);

        // 컬렉션 주인과 아이템 신규 생성자 정보 일치 조회
        validateCollectionAndItem(itemCollection.getMember(), item.getMember());

        // 아이템 크레덴셜 신규 기록 및 지정
        ItemCredential itemCredential = new ItemCredential(UUID.randomUUID().toString(),
                "," + makeNewCredentialRecord(item, itemCollection.getCoin()));
        itemCredentialRepository.save(itemCredential);
        item.assignItemCredential(itemCredential);

        return itemRepository.save(item).getItemId();
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    private ItemCollection getItemCollectionById(String collectionId) {
        return collectionRepository.findCollectionWithCoin(Long.parseLong(collectionId))
                .orElseThrow(() -> new ItemCollectionNotFoundException(Long.parseLong(collectionId)));
    }

    private void validateCollectionAndItem(Member colOwner, Member owner) {
        if (colOwner.getMemberId() != owner.getMemberId()) {
            throw new ItemCreateRequestNotValidException("자기 소유의 컬렉션 아이템만 발행할 수 있습니다.");
        }
    }

    private String makeNewCredentialRecord(Item item, Coin coin) throws Exception {
        StringBuilder record = new StringBuilder();
        record.append(item.getMember().getMemberId()).append("-")
                .append(item.getMember().getMemberId()).append("-")
                .append(coin.getCoinId()).append("-")
                .append(item.getItemPrice());
        return credentialEncryptUtils.encryptRecordByAES256(record.toString());
    }

    @Transactional
    public Long sell(Long itemId, ItemSellRequest itemSellRequest, MemberDetails memberDetails) {
        // 본인 아이템 여부 검증
        Item item = getItemWithOwner(itemId);
        checkValidAuth(item.getMember().getEmail(), memberDetails.getEmail());

        // 판매상태 업데이트
        item.updatePrice(Double.parseDouble(itemSellRequest.getItemPrice()));
        item.updateSaleStatus(true);
        return item.getItemId();
    }

    private Item getItemWithOwner(Long itemId) {
        return itemRepository.findItemWithOwner(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    private void checkValidAuth(String email, String authEmail) {
        if (!email.equals(authEmail)) {
            throw new NotAuthorizedException();
        }
    }

    @Transactional
    public void delete(Long itemId, MemberDetails memberDetails) {
        Item findItem = getItemWithOwner(itemId);
        checkValidAuth(findItem.getMember().getEmail(), memberDetails.getEmail());
        itemRepository.deleteById(itemId);
    }

    public ItemResponse getItem(Long itemId) {
        ItemResponse itemResponse = itemRepository.findItemResponse(itemId);
        if (itemResponse == null) throw new ItemNotFoundException(itemId);

        List<ItemTradeHistoryResponse> tradeHistory = qTransActionRepository.findHistory(itemId);
        List<ItemPriceHistoryResponse> priceHistory = new ArrayList<>();

        if (!tradeHistory.isEmpty()) {
            // itemTradeHistory
            itemResponse.addTradeHistory(tradeHistory);

            // itemPriceHistory
            tradeHistory.forEach(h -> {
                priceHistory.add(new ItemPriceHistoryResponse(h.getTransPrice(), h.getTransDate()));
            });
            itemResponse.addPriceHistory(priceHistory);
        } else {
            itemResponse.addTradeHistory(new ArrayList<>());
            itemResponse.addPriceHistory(new ArrayList<>());
        }

        return itemResponse;
    }

    public List<ItemResponse> getMemberItemList(Long memberId) {
        List<ItemResponse> memberItems = itemRepository.findItemResponseListByMemberId(memberId);
        if (memberItems.size() == 0) memberItems = new ArrayList<>();
        memberItems.sort(ItemResponse::compareTo);
        return memberItems;
    }

    public Page<ItemResponse> getCollectionItemList(Long collectionId, Pageable pageable) {
        return itemRepository.findItemResponsePageByCollectionId(collectionId, pageable);
    }

}