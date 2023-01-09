package com.nfteam.server.exception.support;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class ImageConvertingFailedException extends NFTCustomException {

    private static final String message = "이미지 업로드를 위한 File 컨버팅 작업에 실패했습니다.";

    public ImageConvertingFailedException() {
        super(ExceptionCode.IMAGE_CONVERTING_FAILED, message);
    }
}
