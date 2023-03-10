package com.nfteam.server.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String code;
    private String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(NFTCustomException e) {
        return new ErrorResponse(e.getExceptionCode().getValue(), e.getMessage());
    }

}