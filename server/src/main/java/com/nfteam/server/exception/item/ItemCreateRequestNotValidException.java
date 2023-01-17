package com.nfteam.server.exception.item;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class ItemCreateRequestNotValidException extends NFTCustomException {

    private static final String message = "해당 상품을 발행할 수 없습니다.";

    public ItemCreateRequestNotValidException(String msg) {
        super(ExceptionCode.ITEM_CREATE_FAILED, String.format("%s - 사유 : %s", message, msg));
    }

}
