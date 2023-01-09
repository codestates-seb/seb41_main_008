package com.nfteam.server.item.controller;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.request.item.ItemCreateRequest;
import com.nfteam.server.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity create(@RequestBody ItemCreateRequest itemCreateRequest,
                                 @AuthenticationPrincipal MemberDetails memberDetails) {
        final Long createdId = itemService.save(itemCreateRequest, memberDetails);
        //return ResponseEntity.created(URI.create("/api/items" + createdId)).build();
        return new ResponseEntity(createdId, HttpStatus.CREATED);
    }
}
