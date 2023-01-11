package com.nfteam.server.item.service;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.coin.Coin;
import com.nfteam.server.dto.request.item.CollectionCreateRequest;
import com.nfteam.server.dto.request.item.CollectionPatchRequest;
import com.nfteam.server.dto.response.item.CollectionResponse;
import com.nfteam.server.dto.response.item.ItemResponseDto;
import com.nfteam.server.dto.response.item.UserCollectionResponse;
import com.nfteam.server.exception.auth.NotAuthorizedException;
import com.nfteam.server.exception.item.ItemCollectionNotFoundException;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.entity.ItemCollection;
import com.nfteam.server.item.repository.CollectionRepository;
import com.nfteam.server.item.repository.ItemRepository;
import com.nfteam.server.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long save(CollectionCreateRequest request, MemberDetails memberDetails) {
        ItemCollection itemCollection = request.toCollection();
        itemCollection.assignMember(new Member(memberDetails.getMemberId()));
        itemCollection.assignCoin(new Coin(Long.parseLong(request.getCoinId())));

        return collectionRepository.save(itemCollection).getCollectionId();
    }

    @Transactional
    public Long update(Long collectionId, CollectionPatchRequest request, MemberDetails memberDetails) {
        ItemCollection itemCollection = getCollectionById(collectionId);
        checkValidAuth(itemCollection.getMember().getMemberId(), memberDetails.getMemberId());
        itemCollection.update(request.toCollection());

        return itemCollection.getCollectionId();
    }

    private ItemCollection getCollectionById(Long collectionId) {
        return collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ItemCollectionNotFoundException(collectionId));
    }

    private void checkValidAuth(Long colMemberId, long authMemberId) {
        if (colMemberId != authMemberId) {
            throw new NotAuthorizedException();
        }
    }

    @Transactional
    public Long delete(Long collectionId, MemberDetails memberDetails) {
        ItemCollection itemCollection = getCollectionById(collectionId);
        checkValidAuth(itemCollection.getMember().getMemberId(), memberDetails.getMemberId());
        collectionRepository.deleteById(collectionId);

        return itemCollection.getCollectionId();
    }

    public CollectionResponse getCollection(Long collectionId) {
        ItemCollection itemCollection = collectionRepository.findCollectionWithMemberAndCoin(collectionId)
                .orElseThrow(() -> new ItemCollectionNotFoundException(collectionId));
        CollectionResponse response = itemCollection.toResponse();

        List<Item> items = itemRepository.findItemsByCollectionId(collectionId);
        calcItemMetaInfo(items, response);

        List<ItemResponseDto> itemResponseDtos = items.stream()
                .map(Item::toResponseDto)
                .collect(Collectors.toList());
        itemResponseDtos.forEach(r -> r.addCollectionInfo(itemCollection));
        response.addItemResponseDtos(itemResponseDtos);

        return response;
    }

    private void calcItemMetaInfo(List<Item> items, CollectionResponse response) {
        Integer itemCount = items.size();

        Double totalVolume = items.stream()
                .mapToDouble(i -> i.getCoinCount()).sum();

        Double highestPrice = items.stream()
                .mapToDouble(i -> i.getCoinCount()).max().getAsDouble();

        Double lowestPrice = items.stream()
                .mapToDouble(i -> i.getCoinCount()).min().getAsDouble();

        Long ownerCount = items.stream()
                .map(i -> i.getMember()).distinct().count();

        response.addMetaInfo(itemCount, totalVolume, highestPrice, lowestPrice, ownerCount.intValue());
    }

    public List<UserCollectionResponse> getUserCollection(Long memberId) {
        return collectionRepository.findCollectionByMemberId(memberId)
                .stream().map(collection -> collection.toUserResponse())
                .collect(Collectors.toList());
    }
}
