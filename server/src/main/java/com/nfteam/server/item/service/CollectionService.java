package com.nfteam.server.item.service;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.request.item.CollectionCreateRequest;
import com.nfteam.server.dto.request.item.CollectionPatchRequest;
import com.nfteam.server.exception.auth.NotAuthorizedException;
import com.nfteam.server.exception.item.ItemCollectionNotFoundException;
import com.nfteam.server.item.entity.ItemCollection;
import com.nfteam.server.item.repository.CollectionRepository;
import com.nfteam.server.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollectionService {

    private final CollectionRepository collectionRepository;

    @Transactional
    public Long save(CollectionCreateRequest request, MemberDetails memberDetails) {
        ItemCollection itemCollection = request.toCollection();
        itemCollection.assignMember(new Member(memberDetails.getMemberId()));
        ItemCollection saved = collectionRepository.save(itemCollection);

        return saved.getCollectionId();
    }

    @Transactional
    public Long update(Long collectionId, CollectionPatchRequest request, MemberDetails memberDetails) {
        ItemCollection itemCollection = getCollectionById(collectionId);
        checkValidAuth(itemCollection.getMember().getMemberId(), memberDetails.getMemberId());
        itemCollection.update(request.toCollection());

        return itemCollection.getCollectionId();
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

    private ItemCollection getCollectionById(Long collectionId) {
        return collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ItemCollectionNotFoundException(collectionId));
    }

    public ItemCollection getCollection(Long collectionId) {
        return getCollectionById(collectionId);
    }
}
