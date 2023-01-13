package com.nfteam.server.item.service;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.request.item.CollectionPatchRequest;
import com.nfteam.server.dto.request.item.ItemCreateRequest;
import com.nfteam.server.dto.request.item.ItemPatchRequest;
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

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CollectionRepository collectionRepository;

    @Transactional
    public Long save(ItemCreateRequest itemCreateRequest, MemberDetails memberDetails) {
        Item item = itemCreateRequest.toItem();
        item.assignMember(getMemberByEmail(memberDetails.getEmail()));
        item.assignCollection(getItemCollectionById(itemCreateRequest.getItemCollectionId()));
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

//
//    @Transactional
//    public Long update(Long itemId, ItemPatchRequest itemPatchRequest, MemberDetails memberDetails) {
//        itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(iem));
//    }
}
