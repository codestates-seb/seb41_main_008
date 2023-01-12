package com.nfteam.server.member.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MemberPlatform {

    HOME("HOME"),
    KAKAO("KAKAO"),
    NAVER("NAVER"),
    GOOGLE("GOOGLE");

    private final String value;

    MemberPlatform(final String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}