package com.nfteam.server.domain.item.controller;

import com.nfteam.server.domain.item.service.ItemService;
import com.nfteam.server.dto.request.item.ItemCreateRequest;
import com.nfteam.server.dto.request.item.ItemSellRequest;
import com.nfteam.server.dto.response.common.SingleIdResponse;
import com.nfteam.server.dto.response.common.SliceResponse;
import com.nfteam.server.dto.response.item.ItemResponse;
import com.nfteam.server.security.userdetails.MemberDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<SingleIdResponse> create(@RequestBody @Valid ItemCreateRequest itemCreateRequest,
                                                   @AuthenticationPrincipal MemberDetails memberDetails) throws Exception {
        Long createdId = itemService.save(itemCreateRequest, memberDetails);
        return new ResponseEntity<>(new SingleIdResponse(HttpStatus.CREATED.name(), createdId), HttpStatus.CREATED);
    }

    @PostMapping("/sell/{itemId}")
    public ResponseEntity<SingleIdResponse> sell(@PathVariable("itemId") Long itemId,
                                                 @RequestBody ItemSellRequest itemSellRequest,
                                                 @AuthenticationPrincipal MemberDetails memberDetails) {
        Long updatedId = itemService.sell(itemId, itemSellRequest, memberDetails);
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

    @GetMapping("/collections/{collectionId}")
    public ResponseEntity<SliceResponse> getItemListByCollection(@PathVariable("collectionId") Long collectionId,
                                                                 Pageable pageable) {
        return new ResponseEntity<>(itemService.getCollectionItemList(collectionId, pageable), HttpStatus.OK);
    }

}