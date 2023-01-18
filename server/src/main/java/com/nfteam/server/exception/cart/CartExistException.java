package com.nfteam.server.exception.cart;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class CartExistException extends NFTCustomException {

    private static final String message = "장바구니가 이미 존재합니다.";

    public CartExistException() {
        super(ExceptionCode.MEMBER_EMAIL_EXIST, message);
    }

}
