package com.nfteam.server.exception.item;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class ItemNotFoundException extends NFTCustomException {

    private static final String message = "해당 상품 정보가 존재하지 않습니다.";

    public ItemNotFoundException(Long itemId) {
        super(ExceptionCode.ITEM_NOT_FOUND, String.format("%s - 상품 아이디 : %d", message, itemId));
    }
}
