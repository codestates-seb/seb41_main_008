package com.nfteam.server.item.controller;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.request.item.CollectionCreateRequest;
import com.nfteam.server.dto.request.item.CollectionPatchRequest;
import com.nfteam.server.item.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/collections")
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CollectionCreateRequest request,
                                 @AuthenticationPrincipal MemberDetails memberDetails) {
        Long createdId = collectionService.save(request, memberDetails);
        return ResponseEntity.created(URI.create("/api/collections" + createdId)).build();
    }

    @PatchMapping("/{collectionId}")
    public ResponseEntity update(@PathVariable("collectionId") Long collectionId,
                                 @RequestBody CollectionPatchRequest request,
                                 @AuthenticationPrincipal MemberDetails memberDetails) {
        Long updatedId = collectionService.update(collectionId, request, memberDetails);
        return ResponseEntity.created(URI.create("/api/collections" + updatedId)).build();
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity delete(@PathVariable("collectionId") Long collectionId,
                                 @AuthenticationPrincipal MemberDetails memberDetails) {
        collectionService.delete(collectionId, memberDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity getCollection(@PathVariable("collectionId") Long collectionId) {
        collectionService.getCollection(collectionId);
        return ResponseEntity.ok().build();
    }


}
