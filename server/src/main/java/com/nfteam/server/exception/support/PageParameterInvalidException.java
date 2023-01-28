package com.nfteam.server.exception.support;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class PageParameterInvalidException extends NFTCustomException {

    private static final String message = "요청 page/size 파라미터 값이 올바르지 않습니다.";

    public PageParameterInvalidException() {
        super(ExceptionCode.PAGE_PARAM_INVALID, message);
    }

}