package com.nfteam.server.exception.cart;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class CartItemNotSaleException extends NFTCustomException {

    public CartItemNotSaleException(String message) {
        super(ExceptionCode.ITEM_NOT_ON_SALE, message);
    }

}