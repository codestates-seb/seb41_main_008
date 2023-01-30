package com.nfteam.server.exception.coin;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class CoinOrderNotFoundException extends NFTCustomException {

    private static final String message = "해당 코인 주문 정보가 존재하지 않습니다.";

    public CoinOrderNotFoundException(String tid) {
        super(ExceptionCode.COIN_PAYMENT_FAILED, String.format("%s - 주문 tid : %s", message, tid));
    }

}