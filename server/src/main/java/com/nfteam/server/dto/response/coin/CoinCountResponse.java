package com.nfteam.server.dto.response.coin;

import lombok.Getter;

@Getter
public class CoinCountResponse {

    private final String coinName;
    private final Double coinCount;


    public CoinCountResponse(String coinName, Double coinCount) {
        this.coinName=coinName;
        this.coinCount=coinCount;
    }
}
