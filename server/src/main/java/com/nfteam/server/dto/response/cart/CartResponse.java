package com.nfteam.server.dto.response.cart;

import com.nfteam.server.dto.response.item.ItemResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CartResponse {

    private Long cartId;
    private List<CartItemResponse> items;

    @Builder
    public CartResponse(Long cartId, List<ItemResponse> items) {
        this.cartId = cartId;
        this.items = items.stream()
                .map(item -> CartItemResponse.of(item))
                .collect(Collectors.toList());
    }

    public CartResponse(Long cartId) {
        this.cartId = cartId;
    }

}