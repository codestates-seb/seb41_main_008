package com.nfteam.server.item.service;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.request.item.ItemCreateRequest;
import com.nfteam.server.exception.item.ItemCollectionNotFoundException;
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

        item.assignMember(new Member(1L));
        item.assignCollection(new ItemCollection(Long.parseLong(itemCreateRequest.getItemCollectionId())));
        itemRepository.save(item);
        return item.getItemId();
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private ItemCollection getItemCollectionInfo(String collectionId) {
        return collectionRepository.findById(Long.parseLong(collectionId))
                .orElseThrow(() -> new ItemCollectionNotFoundException(Long.parseLong(collectionId)));
    }
}
