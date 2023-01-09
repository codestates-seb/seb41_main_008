package com.nfteam.server.exception.token;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class AccessTokenExpiredException extends NFTCustomException {

    private static final String message = "액세스토큰이 만료되었습니다. 재발급이 필요합니다";
    public AccessTokenExpiredException() {
        super(ExceptionCode.TOKEN_EXPIRED, message);
    }
}
