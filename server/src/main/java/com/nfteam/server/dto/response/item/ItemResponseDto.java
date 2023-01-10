package com.nfteam.server.dto.response.item;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemResponseDto {

    private Long itemId;
    private String itemName;
    private String itemImageName;
    private String priceCoin;
    private Double priceCoinCount;
    private boolean onSale;

    @Builder
    public ItemResponseDto(Long itemId, String itemName, String itemImageName, String priceCoin, Double priceCoinCount, boolean onSale) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageName = itemImageName;
        this.priceCoin = priceCoin;
        this.priceCoinCount = priceCoinCount;
        this.onSale = onSale;
    }
}
