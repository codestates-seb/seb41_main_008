package com.nfteam.server.exception.token;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class TokenNotValidateException extends NFTCustomException {

    private static final String message = "해당 토큰은 유효하지 않은 토큰입니다";

    public TokenNotValidateException(String text) {
        super(ExceptionCode.TOKEN_EXTRACT_FAILED, String.format("%s - 사유 : %s", message, text));
    }

}
