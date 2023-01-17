package com.nfteam.server.item.controller;

import com.nfteam.server.dto.request.item.ItemCreateRequest;
import com.nfteam.server.dto.request.item.ItemPatchRequest;
import com.nfteam.server.dto.response.common.SingleIdResponse;
import com.nfteam.server.dto.response.item.ItemResponse;
import com.nfteam.server.item.service.ItemService;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid ItemCreateRequest itemCreateRequest,
                                 @AuthenticationPrincipal MemberDetails memberDetails) throws Exception {
        Long createdId = itemService.save(itemCreateRequest, memberDetails);
        return new ResponseEntity<>(new SingleIdResponse(HttpStatus.CREATED.name(), createdId), HttpStatus.CREATED);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity update(@PathVariable("itemId") Long itemId,
                                 @RequestBody ItemPatchRequest itemPatchRequest,
                                 @AuthenticationPrincipal MemberDetails memberDetails) {
        Long updatedId = itemService.update(itemId, itemPatchRequest, memberDetails);
        return new ResponseEntity<>(new SingleIdResponse(HttpStatus.OK.name(), updatedId), HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity delete(@PathVariable("itemId") Long itemId,
                                 @AuthenticationPrincipal MemberDetails memberDetails) {
        itemService.delete(itemId, memberDetails);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable("itemId") Long itemId) {
        return new ResponseEntity<>(itemService.getItem(itemId), HttpStatus.OK);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ItemResponse>> getMemberItem(@PathVariable("memberId") Long memberId) {
        return new ResponseEntity<>(itemService.getMemberItemList(memberId), HttpStatus.OK);
    }

}
