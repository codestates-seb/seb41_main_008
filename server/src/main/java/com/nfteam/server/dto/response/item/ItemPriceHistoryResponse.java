package com.nfteam.server.dto.response.item;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ItemPriceHistoryResponse {

    private Double transPrice;
    private String transDate;

    @Builder
    public ItemPriceHistoryResponse(Double transPrice,
                                    LocalDateTime transDate) {
        this.transPrice = transPrice;
        this.transDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(transDate);
    }

}
