package com.nfteam.server.exception.token;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class RefreshTokenExpiredException extends NFTCustomException {
    private static final String message = "리프레시 토큰이 만료되었습니다.";

    public RefreshTokenExpiredException() {
        super(ExceptionCode.TOKEN_EXPIRED, message);
    }

}