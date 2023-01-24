package com.nfteam.server.exception.cart;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class CartNotFoundException extends NFTCustomException {

    private static final String message = "장바구니가 생성되지 않았습니다. 관리자에게 문의하세요.(기록 이상)";

    public CartNotFoundException() {
        super(ExceptionCode.CART_NOT_FOUND, message);
    }

}