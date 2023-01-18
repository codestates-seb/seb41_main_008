package com.nfteam.server.dto.response.item;

import lombok.Builder;
import lombok.Getter;

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

    // 거래 당시 코인 수수료
    private Double coinWithdrawFee;

    // 거래일자
    private String transDate;

    @Builder
    public ItemTradeHistoryResponse(Long sellerId,
                                    String sellerName,
                                    Long buyerId,
                                    String buyerName,
                                    Double transPrice,
                                    Double coinWithdrawFee,
                                    String transDate) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.transPrice = transPrice;
        this.coinWithdrawFee = coinWithdrawFee;
        this.transDate = transDate;
    }

}
