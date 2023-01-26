package com.nfteam.server.exception.coin;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class CoinPaymentFailedException extends NFTCustomException {

    private static final String message = "코인 결제 처리에 실패하였습니다.";

    public CoinPaymentFailedException() {
        super(ExceptionCode.COIN_PAYMENT_FAILED, message);
    }

}