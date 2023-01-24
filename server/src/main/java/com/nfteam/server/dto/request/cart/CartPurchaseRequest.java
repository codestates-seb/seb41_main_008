package com.nfteam.server.dto.request.cart;

import java.util.List;

import lombok.Getter;

@Getter
public class CartPurchaseRequest {

    private Long cartId;
    private List<Long> itemIdList;
    private Double totalPrice;

}