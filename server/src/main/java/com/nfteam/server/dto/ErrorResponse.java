package com.nfteam.server.dto;

import com.nfteam.server.exception.NFTCustomException;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private String exceptionCode;
    private String exceptionMessage;

    private ErrorResponse() {
    }

    public ErrorResponse(String exceptionCode, String exceptionMessage) {
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }

    public static ErrorResponse of(NFTCustomException e){
        return new ErrorResponse(e.getExceptionCode().getValue(), e.getMessage());
    }

}
