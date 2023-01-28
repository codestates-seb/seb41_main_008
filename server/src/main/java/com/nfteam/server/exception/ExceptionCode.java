package com.nfteam.server.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExceptionCode {

    //COMMON
    RUNTIME_ERROR("RUNTIME_ERROR"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    INVALID_METHOD_ARGS("INVALID_METHOD_ARGS"),
    INVALID_PATH_VARIABLE_ARGS("INVALID_PATH_VARIABLE_ARGS"),

    // MEMBER
    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND"),
    MEMBER_EMAIL_EXIST("MEMBER_EMAIL_EXIST"),
    MEMBER_NOT_ACTIVE("MEMBER_NOT_ACTIVE"),

    // ITEM
    ITEM_NOT_FOUND("ITEM_NOT_FOUND"),
    ITEM_NOT_ON_SALE("ITEM_NOT_ON_SALE"),
    ITEM_CREATE_FAILED("ITEM_CREATE_FAILED"),
    ITEM_COLLECTION_NOT_FOUND("ITEM_COLLECTION_NOT_FOUND"),

    // SUPPORT
    IMAGE_CONVERTING_FAILED("IMAGE_CONVERTING_FAILED"),
    PAGE_PARAM_INVALID("PAGE_PARAM_INVALID"),
    URL_LENGTH_INVALID("URL_LENGTH_INVALID"),

    // AUTH
    NOT_AUTHORIZED("NOT_AUTHORIZED"),
    NOT_SUPPORTED_PLATFORM("NOT_SUPPORTED_PLATFORM"),

    //TOKEN
    TOKEN_EXPIRED("TOKEN_EXPIRED"),
    TOKEN_EXTRACT_FAILED("TOKEN_EXTRACT_FAILED"),

    // CART
    CART_NOT_FOUND("CART NOT FOUND"),
    CART_ALREADY_EXIST("CART_ALREADY_EXIST"),

    // COIN
    COIN_NOT_FOUND("COIN_NOT_FOUND"),
    COIN_PAYMENT_FAILED("COIN_PAYMENT_FAILED"),

    // RANK
    RANK_NOT_FOUND("RANK_NOT_FOUND"),

    //TRANSACTION
    TRANSACTION_FAILED("TRANSACTION_FAILED");

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