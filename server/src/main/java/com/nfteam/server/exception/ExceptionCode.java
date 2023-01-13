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

    // ITEM
    ITEM_NOT_FOUND("ITEM_NOT_FOUND"),
    ITEM_COLLECTION_NOT_FOUND("ITEM_COLLECTION_NOT_FOUND"),

    // SUPPORT
    IMAGE_CONVERTING_FAILED("IMAGE_CONVERTING_FAILED"),

    //TOKEN
    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND"),
    TOKEN_EXPIRED("TOKEN_EXPIRED"),
    TOKEN_EXTRACT_FAILED("TOKEN_EXTRACT_FAILED");

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