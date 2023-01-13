package com.nfteam.server.dto.response.item;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemPriceHistoryResponse {

    // 거래 당사자
    private Long sellerId;
    private String sellerName;
    private Long buyerId;
    private String buyerName;

    // 거래 가격
    private String transCoinName;
    private Double transPrice;
    private Double transPriceWon;

    // 거래일자
    private String transDate;
    private String dDay;

    @Builder
    public ItemPriceHistoryResponse(Long sellerId, String sellerName,
                                    Long buyerId, String buyerName,
                                    String transCoinName,
                                    Double transPrice,
                                    Double transPriceWon,
                                    String transDate,
                                    String dDay) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.transCoinName = transCoinName;
        this.transPrice = transPrice;
        this.transPriceWon = transPriceWon;
        this.transDate = transDate;
        this.dDay = dDay;
    }
}
