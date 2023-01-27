package com.nfteam.server.dto.response.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ItemTradeHistoryResponse {

    // 판매자
    private Long sellerId;
    private String sellerName;

    // 구매자
    private Long buyerId;
    private String buyerName;

    // 거래 가격 == 코인 갯수
    private Double transPrice;

    // 거래 코인명
    private String coinName;

    // 거래일자
    private String transDate;

    @Builder
    @QueryProjection
    public ItemTradeHistoryResponse(Long sellerId,
                                    String sellerName,
                                    Long buyerId,
                                    String buyerName,
                                    Double transPrice,
                                    String coinName,
                                    LocalDateTime transDate) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.transPrice = transPrice;
        this.coinName = coinName;
        this.transDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(transDate);
    }

}