package com.nfteam.server.item.service;

import com.nfteam.server.coin.entity.Coin;
import com.nfteam.server.common.utils.CredentialEncryptUtils;
import com.nfteam.server.dto.request.item.ItemCreateRequest;
import com.nfteam.server.dto.request.item.ItemSellRequest;
import com.nfteam.server.dto.response.item.ItemResponse;
import com.nfteam.server.exception.auth.NotAuthorizedException;
import com.nfteam.server.exception.item.ItemCollectionNotFoundException;
import com.nfteam.server.exception.item.ItemCreateRequestNotValidException;
import com.nfteam.server.exception.item.ItemNotFoundException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.entity.ItemCollection;
import com.nfteam.server.item.entity.ItemCredential;
import com.nfteam.server.item.repository.ItemCollectionRepository;
import com.nfteam.server.item.repository.ItemRepository;
import com.nfteam.server.item.repository.QItemRepository;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemCollectionRepository collectionRepository;
    private final ItemRepository itemRepository;
    private final QItemRepository qItemRepository;
    private final MemberRepository memberRepository;
    private final CredentialEncryptUtils credentialEncryptUtils;

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

        // 아이템 크레덴셜 신규 기록
        ItemCredential itemCredential = new ItemCredential(UUID.randomUUID().toString(),
                "," + makeNewCredentialRecord(item, itemCollection.getCoin()));
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
        ItemResponse itemResponse = qItemRepository.findItem(itemId);
        if (itemResponse == null) throw new ItemNotFoundException(itemId);
        return itemResponse;
    }

    public List<ItemResponse> getMemberItemList(Long memberId) {
        List<ItemResponse> memberItems = qItemRepository.findItemByMember(memberId);
        if (memberItems.size() == 0) memberItems = new ArrayList<>();
        memberItems.sort(ItemResponse::compareTo);
        return memberItems;
    }

}