package com.nfteam.server.exception.cart;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class CartExistException extends NFTCustomException {

    private static final String message = "결제 미완료된 장바구니가 존재합니다. (기록 이상)";

    public CartExistException() {
        super(ExceptionCode.MEMBER_EMAIL_EXIST, message);
    }

}