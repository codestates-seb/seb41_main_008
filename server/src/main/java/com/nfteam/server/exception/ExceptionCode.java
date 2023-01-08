package com.nfteam.server.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExceptionCode {

    //COMMON
    INTERNAL_SERVER_ERROR("9000"),
    INVALID_METHOD_ARGS("9001"),
    INVALID_PATH_VARIABLE_ARGS("9002"),
    INVALID_HTTP_REQUEST("9003"),

    // MEMBER
    MEMBER_NOT_FOUND("2001"),
    MEMBER_EMAIL_EXIST("2002"),

    // AUTH
    NOT_AUTHORIZED("3001"),
    TOKEN_NOT_FOUND("3002"),

    // ITEM
    ITEM_NOT_FOUND("4001");

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