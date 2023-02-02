package com.nfteam.server.domain.item.service;

import com.nfteam.server.domain.coin.entity.Coin;
import com.nfteam.server.domain.item.entity.Item;
import com.nfteam.server.domain.item.entity.ItemCollection;
import com.nfteam.server.domain.item.repository.ItemCollectionRepository;
import com.nfteam.server.domain.item.repository.ItemRepository;
import com.nfteam.server.domain.member.entity.Member;
import com.nfteam.server.domain.member.repository.MemberRepository;
import com.nfteam.server.dto.request.item.CollectionCreateRequest;
import com.nfteam.server.dto.request.item.CollectionPatchRequest;
import com.nfteam.server.dto.response.item.CollectionItemResponse;
import com.nfteam.server.dto.response.item.CollectionMainResponse;
import com.nfteam.server.dto.response.item.CollectionOnlyResponse;
import com.nfteam.server.dto.response.item.CollectionResponse;
import com.nfteam.server.dto.response.member.MemberCollectionResponse;
import com.nfteam.server.exception.auth.NotAuthorizedException;
import com.nfteam.server.exception.item.ItemCollectionNotFoundException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.security.userdetails.MemberDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class CollectionService {

    private final ItemCollectionRepository itemCollectionRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public CollectionService(ItemCollectionRepository itemCollectionRepository,
                             ItemRepository itemRepository,
                             MemberRepository memberRepository) {
        this.itemCollectionRepository = itemCollectionRepository;
        this.itemRepository = itemRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long save(CollectionCreateRequest request, MemberDetails memberDetails) {
        ItemCollection itemCollection = request.toCollection();
        Member member = findMemberByEmail(memberDetails.getEmail());
        itemCollection.assignMember(member);
        itemCollection.assignCoin(new Coin(Long.parseLong(request.getCoinId())));
        return itemCollectionRepository.save(itemCollection).getCollectionId();
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    @Transactional
    public Long update(Long collectionId, CollectionPatchRequest request, MemberDetails memberDetails) {
        ItemCollection itemCollection = getCollectionById(collectionId);
        checkValidAuth(itemCollection.getMember().getEmail(), memberDetails.getEmail());
        itemCollection.update(request.toCollection());
        return itemCollection.getCollectionId();
    }

    private ItemCollection getCollectionById(Long collectionId) {
        return itemCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new ItemCollectionNotFoundException(collectionId));
    }

    private void checkValidAuth(String savedEmail, String authEmail) {
        if (!savedEmail.equals(authEmail)) {
            throw new NotAuthorizedException();
        }
    }

    @Transactional
    public void delete(Long collectionId, MemberDetails memberDetails) {
        ItemCollection itemCollection = getCollectionById(collectionId);
        checkValidAuth(itemCollection.getMember().getEmail(), memberDetails.getEmail());
        itemCollectionRepository.deleteById(collectionId);
    }

    public CollectionResponse getCollection(Long collectionId) {
        ItemCollection itemCollection = getCollectionWithMemberAndCoin(collectionId);
        List<Item> items = itemRepository.findItemsWithOwnerByCollectionId(collectionId);
        items.stream().forEach(i -> i.assignCollection(itemCollection));

        CollectionResponse response = itemCollection.toResponse();

        // 아이템 메타정보 계산
        if (items.size() != 0) {
            calcItemMetaInfo(items, response);
        } else {
            response.addMetaInfo(0, 0.0, 0.0, 0.0, 0);
        }

        List<CollectionItemResponse> itemResponses = items.stream()
                .map(CollectionItemResponse::of)
                .sorted(CollectionItemResponse::compareTo)
                .collect(Collectors.toList());
        response.addItemResponseList(itemResponses);

        return response;
    }

    private ItemCollection getCollectionWithMemberAndCoin(Long collectionId) {
        return itemCollectionRepository.findCollectionWithMemberAndCoin(collectionId)
                .orElseThrow(() -> new ItemCollectionNotFoundException(collectionId));
    }

    private void calcItemMetaInfo(List<Item> items, CollectionResponse response) {
        Integer itemCount = items.size();
        Double totalVolume = items.stream().mapToDouble(i -> i.getItemPrice()).sum();
        Long ownerCount = items.stream().map(i -> i.getMember()).distinct().count();

        Stream<Item> SalesItemStream = items.stream().filter(i -> i.getOnSale());
        double highestPrice = SalesItemStream.mapToDouble(i -> i.getItemPrice()).max().getAsDouble();

        Stream<Item> SalesItemStream2 = items.stream().filter(i -> i.getOnSale());
        double lowestPrice = SalesItemStream2.mapToDouble(i -> i.getItemPrice()).min().getAsDouble();

        response.addMetaInfo(itemCount, totalVolume, highestPrice, lowestPrice, ownerCount.intValue());
    }

    public CollectionOnlyResponse getCollectionInfoOnly(Long collectionId) {
        ItemCollection itemCollection = getCollectionWithMemberAndCoin(collectionId);
        CollectionOnlyResponse response = CollectionOnlyResponse.of(itemCollection);

        List<Item> items = itemRepository.findItemsWithOwnerByCollectionId(collectionId);
        // 아이템 메타정보 계산
        if (items.size() != 0) {
            calcCollectionOnlyItemMetaInfo(items, response);
        } else {
            response.addMetaInfo(0, 0.0, 0.0, 0.0, 0);
        }

        return response;
    }

    private void calcCollectionOnlyItemMetaInfo(List<Item> items, CollectionOnlyResponse response) {
        Integer itemCount = items.size();
        Double totalVolume = items.stream().mapToDouble(i -> i.getItemPrice()).sum();
        Long ownerCount = items.stream().map(i -> i.getMember()).distinct().count();

        Stream<Item> SalesItemStream = items.stream().filter(i -> i.getOnSale());
        double highestPrice = SalesItemStream.mapToDouble(i -> i.getItemPrice()).max().getAsDouble();

        Stream<Item> SalesItemStream2 = items.stream().filter(i -> i.getOnSale());
        double lowestPrice = SalesItemStream2.mapToDouble(i -> i.getItemPrice()).min().getAsDouble();

        response.addMetaInfo(itemCount, totalVolume, highestPrice, lowestPrice, ownerCount.intValue());
    }

    public List<MemberCollectionResponse> getMemberCollectionList(Long memberId) {
        return itemCollectionRepository.findCollectionListWithCoinByMemberId(memberId)
                .stream().map(collection -> collection.toMemberResponse())
                .collect(Collectors.toList());
    }

    public Page<CollectionMainResponse> getMainCollectionList(Pageable pageable) {
        return itemCollectionRepository.findCollectionOnlyResponsePage(pageable);
    }

}