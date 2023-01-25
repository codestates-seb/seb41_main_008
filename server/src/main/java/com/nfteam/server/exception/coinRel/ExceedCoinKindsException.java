package com.nfteam.server.exception.coinRel;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class ExceedCoinKindsException extends NFTCustomException {

    private static final String message="해당 아이디에는 종류가 넘어가는 수의 코인 갯수가 존재합니다";


    public ExceedCoinKindsException() {
        super(ExceptionCode.EXCEED_COIN_COUNTS, message);
    }
}
