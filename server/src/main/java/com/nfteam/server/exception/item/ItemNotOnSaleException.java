package com.nfteam.server.exception.item;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class ItemNotOnSaleException extends NFTCustomException {

    private static final String message = "해당 상품은 현재 거래 가능한 상품이 아닙니다.";

    public ItemNotOnSaleException(Long itemId) {
        super(ExceptionCode.ITEM_NOT_ON_SALE, String.format("%s - 상품 아이디 : %d", message, itemId));
    }

}