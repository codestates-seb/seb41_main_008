package com.nfteam.server.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExceptionCode {

    //COMMON
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    INVALID_METHOD_ARGS("INVALID_METHOD_ARGS"),
    INVALID_PATH_VARIABLE_ARGS("INVALID_PATH_VARIABLE_ARGS"),
    INVALID_HTTP_REQUEST("INVALID_HTTP_REQUEST"),

    // MEMBER
    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND"),
    MEMBER_EMAIL_EXIST("MEMBER_EMAIL_EXIST"),

    // AUTH
    NOT_AUTHORIZED("NOT_AUTHORIZED"),
    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND"),

    // ITEM
    ITEM_NOT_FOUND("ITEM_NOT_FOUND");

    private final String value;

    ExceptionCode(final String value) {
        this.value = value;
    }

    // JSON 변환될 때 직렬화/역직렬화 되는 값 지정
    @JsonValue
    public String getValue() {
        return value;
    }

}