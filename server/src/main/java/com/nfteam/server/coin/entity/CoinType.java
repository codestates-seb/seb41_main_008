package com.nfteam.server.coin.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CoinType {
    SOL(1L),
    BTC(2L),
    DOGE(3L),
    ETH(4L),
    ETC(5L);

    private final Long coinNum;

    CoinType(final Long coinNum) {
        this.coinNum = coinNum;
    }

    @JsonValue
    public Long getValue() {
        return coinNum;
    }

}
