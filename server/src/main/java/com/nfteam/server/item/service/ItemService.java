package com.nfteam.server.item.service;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.request.item.ItemCreateRequest;
import com.nfteam.server.exception.item.ItemCollectionNotFoundException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.entity.ItemCollection;
import com.nfteam.server.item.repository.ItemCollectionRepository;
import com.nfteam.server.item.repository.ItemRepository;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import com.nfteam.server.support.S3ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final ItemCollectionRepository itemCollectionRepository;
    private final S3ImageUploader s3ImageUploader;

    @Transactional
    public Long save(ItemCreateRequest itemCreateRequest, MemberDetails memberDetails) {
        Item item = itemCreateRequest.toItem();

        // itemCredential + collection + member 정보 아이템에 추가
        Member member = getMemberById(memberDetails.getMemberId());
        ItemCollection itemCollection = getItemCollectionInfo(itemCreateRequest.getItemCollectionId());
        item.assignCollectionAndMember(itemCollection, member);
        itemRepository.save(item);

        return item.getItemId();
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private ItemCollection getItemCollectionInfo(String collectionId) {
        return itemCollectionRepository.findById(Long.parseLong(collectionId))
                .orElseThrow(() -> new ItemCollectionNotFoundException(Long.parseLong(collectionId)));
    }
}
