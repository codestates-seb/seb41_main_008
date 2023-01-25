package com.nfteam.server.dto.response.item;

import lombok.Getter;

@Getter
public class ItemPriceHistoryResponse {

    private Double transPrice;
    private String transDate;

    public ItemPriceHistoryResponse(Double transPrice, String transDate) {
        this.transPrice = transPrice;
        this.transDate = transDate;
    }

}