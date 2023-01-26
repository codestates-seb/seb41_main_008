package com.nfteam.server.exception.auth;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class NotSupportedPlatformException extends NFTCustomException {

    private static final String message = "지원되지 않는 소셜 로그인 플래폼 입니다.";

    public NotSupportedPlatformException() {
        super(ExceptionCode.NOT_SUPPORTED_PLATFORM, message);
    }

}
