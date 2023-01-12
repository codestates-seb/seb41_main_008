package com.nfteam.server.item.controller;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.request.item.ItemCreateRequest;
import com.nfteam.server.dto.response.common.SingleIdResponse;
import com.nfteam.server.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity create(@RequestBody ItemCreateRequest itemCreateRequest,
                                 @AuthenticationPrincipal MemberDetails memberDetails) {
        Long createdId = itemService.save(itemCreateRequest, memberDetails);
        return new ResponseEntity<>(new SingleIdResponse(HttpStatus.CREATED.name(), createdId), HttpStatus.CREATED);
    }
}
