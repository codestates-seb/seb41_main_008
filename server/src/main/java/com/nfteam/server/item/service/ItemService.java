package com.nfteam.server.item.service;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.request.item.ItemCreateRequest;
import com.nfteam.server.dto.request.item.ItemPatchRequest;
import com.nfteam.server.dto.response.item.ItemResponse;
import com.nfteam.server.exception.auth.NotAuthorizedException;
import com.nfteam.server.exception.item.ItemCollectionNotFoundException;
import com.nfteam.server.exception.item.ItemNotFoundException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.entity.ItemCollection;
import com.nfteam.server.item.repository.CollectionRepository;
import com.nfteam.server.item.repository.ItemRepository;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final CollectionRepository collectionRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(ItemCreateRequest request, MemberDetails memberDetails) {
        Item item = request.toItem();
        item.assignMember(getMemberByEmail(memberDetails.getEmail()));
        item.assignCollection(getItemCollectionById(request.getItemCollectionId()));
        itemRepository.save(item);
        return item.getItemId();
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    private ItemCollection getItemCollectionById(String collectionId) {
        return collectionRepository.findById(Long.parseLong(collectionId))
                .orElseThrow(() -> new ItemCollectionNotFoundException(Long.parseLong(collectionId)));
    }

    @Transactional
    public Long update(Long itemId, ItemPatchRequest request, MemberDetails memberDetails) {
        Item item = findItem(itemId);
        checkValidAuth(item.getMember().getEmail(), memberDetails.getEmail());
        item.update(request.toItem());
        return item.getItemId();
    }

    private void checkValidAuth(String email, String authEmail) {
        if(!email.equals(authEmail)){
            throw new NotAuthorizedException();
        }
    }

    private Item findItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException(itemId));
    }

    @Transactional
    public void delete(Long itemId, MemberDetails memberDetails) {
        Item findItem = findItem(itemId);
        checkValidAuth(findItem.getMember().getEmail(), memberDetails.getEmail());
        itemRepository.deleteById(itemId);
    }


    public ItemResponse getItem(Long itemId) {

        return null;
    }
}
