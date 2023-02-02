package com.nfteam.server.exception.cart;

import lombok.Getter;

import java.util.List;

@Getter
public class CartErrorResponse {

    private String message;
    private List<Long> idList;

    public CartErrorResponse(String message, List<Long> idList) {
        this.message = message;
        this.idList = idList;
    }

    public static CartErrorResponse of(CartItemNotSaleException e) {
        return new CartErrorResponse(e.getMessage(), e.getIdList());
    }

}