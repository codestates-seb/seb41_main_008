package com.nfteam.server.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MemberRole {

    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    MemberRole(final String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}