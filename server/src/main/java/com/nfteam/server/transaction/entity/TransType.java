package com.nfteam.server.transaction.entity;

import lombok.Getter;

public enum TransType {
    SALE(0),
    BUY(1);

    @Getter
    private Integer value;

    TransType(Integer value) {
        this.value = value;
    }
}
