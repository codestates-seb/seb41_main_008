package com.nfteam.server.exception.coinRel;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class CoinNotFoundException extends NFTCustomException {

    private static final String message = "해당 코인 정보가 존재하지 않습니다.";

    public CoinNotFoundException(Long coinId) {
        super(ExceptionCode.COIN_NOT_FOUND, String.format("%s - 코인 아이디 : %d", message, coinId));
    }

    public CoinNotFoundException(String coinName) {
        super(ExceptionCode.COIN_NOT_FOUND, String.format("%s - 코인 이름 : %s", message, coinName));
    }

}