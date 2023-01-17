package com.nfteam.server.exception.cart;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class CartNotFoundException extends NFTCustomException {

    private static final String message = "장바구니를 먼저 생성해주세요";

    public CartNotFoundException() {
        super(ExceptionCode.CART_NOT_FOUND, message);
    }

}
