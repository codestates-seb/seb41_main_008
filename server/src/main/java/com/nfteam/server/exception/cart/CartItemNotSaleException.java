package com.nfteam.server.exception.cart;

import java.util.List;

public class CartItemNotSaleException extends RuntimeException {

    private static final String message = "해당 장바구니 상품들은 판매되는 상품들이 아닙니다.";
    private final List<Long> idList;

    public CartItemNotSaleException(List<Long> idList) {
        super(message);
        this.idList = idList;
    }

    public List<Long> getIdList() {
        return idList;
    }

}