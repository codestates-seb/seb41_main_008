package com.nfteam.server.openfeign.model;

import lombok.Getter;

@Getter
public class UpbitCoinPrice {

    private String market;
    private double trade_price;

}
