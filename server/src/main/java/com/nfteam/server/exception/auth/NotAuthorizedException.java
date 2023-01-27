package com.nfteam.server.exception.auth;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class NotAuthorizedException extends NFTCustomException {

    private static final String message = "해당 정보 접근에는 관리자 권한이 필요합니다";

    public NotAuthorizedException() {
        super(ExceptionCode.NOT_AUTHORIZED, message);
    }

}