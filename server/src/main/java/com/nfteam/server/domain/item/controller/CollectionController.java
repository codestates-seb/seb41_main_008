package com.nfteam.server.domain.item.controller;

import com.nfteam.server.domain.item.service.CollectionService;
import com.nfteam.server.dto.request.item.CollectionCreateRequest;
import com.nfteam.server.dto.request.item.CollectionPatchRequest;
import com.nfteam.server.dto.response.common.SingleIdResponse;
import com.nfteam.server.dto.response.item.CollectionOnlyResponse;
import com.nfteam.server.dto.response.item.CollectionResponse;
import com.nfteam.server.security.userdetails.MemberDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CollectionCreateRequest request,
                                 @AuthenticationPrincipal MemberDetails memberDetails) {
        Long createdId = collectionService.save(request, memberDetails);
        return new ResponseEntity<>(new SingleIdResponse(HttpStatus.CREATED.name(), createdId), HttpStatus.CREATED);
    }

    @PatchMapping("/{collectionId}")
    public ResponseEntity update(@PathVariable("collectionId") Long collectionId,
                                 @RequestBody CollectionPatchRequest request,
                                 @AuthenticationPrincipal MemberDetails memberDetails) {
        Long updatedId = collectionService.update(collectionId, request, memberDetails);
        return new ResponseEntity<>(new SingleIdResponse(HttpStatus.OK.name(), updatedId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity delete(@PathVariable("collectionId") Long collectionId,
                                 @AuthenticationPrincipal MemberDetails memberDetails) {
        collectionService.delete(collectionId, memberDetails);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<CollectionResponse> getCollection(@PathVariable("collectionId") Long collectionId) {
        return new ResponseEntity<>(collectionService.getCollection(collectionId), HttpStatus.OK);
    }

    @GetMapping("/only/{collectionId}")
    public ResponseEntity<CollectionOnlyResponse> getCollectionInfoOnly(@PathVariable("collectionId") Long collectionId) {
        return new ResponseEntity<>(collectionService.getCollectionInfoOnly(collectionId), HttpStatus.OK);
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity getUserCollection(@PathVariable("memberId") Long memberId) {
        return new ResponseEntity<>(collectionService.getMemberCollectionList(memberId), HttpStatus.OK);
    }

    @GetMapping("/main")
    public ResponseEntity getMainCollection(Pageable pageable) {
        return new ResponseEntity<>(collectionService.getMainCollectionList(pageable).getContent(), HttpStatus.OK);
    }

}