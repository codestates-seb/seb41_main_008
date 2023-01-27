package com.nfteam.server.exception;

import lombok.Getter;

@Getter
public class NFTCustomException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public NFTCustomException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

}