package com.nfteam.server.exception.token;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class ExtractTokenFailedException extends NFTCustomException {
    private static final String message = "토큰 정보 추출에 실패했습니다. 올바른 토큰 정보인지 확인해 주세요.";

    public ExtractTokenFailedException() {
        super(ExceptionCode.TOKEN_EXTRACT_FAILED, message);
    }
}
