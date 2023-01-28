package com.nfteam.server.exception.support;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class UrlLengthInvalidException extends NFTCustomException {

    private static final String message = "요청 URL 길이는 800자를 넘을 수 없습니다.";

    public UrlLengthInvalidException() {
        super(ExceptionCode.URL_LENGTH_INVALID, message);
    }

}