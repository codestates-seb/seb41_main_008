package com.nfteam.server.exception.transaction;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class TransRecordNotValidException extends NFTCustomException {

    private static final String message = "해당 거래는 올바르지 않은 거래입니다.";

    public TransRecordNotValidException(String msg) {
        super(ExceptionCode.MEMBER_EMAIL_EXIST, String.format("%s - 문제 사유 : %s", message, msg));
    }

}
