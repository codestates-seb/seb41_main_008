package com.nfteam.server.dto.response.coin;

import com.nfteam.server.domain.coin.entity.CoinOrder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CoinOrderResponse {

    private Long orderId;
    private String tid;
    private Long coinId;
    private String coinName;
    private String coinImage;
    private Double coinCount;
    private Double totalPrice;
    private String payStatus;

    @Builder
    public CoinOrderResponse(Long orderId,
                             String tid,
                             Long coinId,
                             String coinName,
                             String coinImage,
                             Double coinCount,
                             Double totalPrice,
                             Boolean payStatus) {
        this.orderId = orderId;
        this.tid = tid;
        this.coinId = coinId;
        this.coinName = coinName;
        this.coinImage = coinImage;
        this.coinCount = coinCount;
        this.totalPrice = totalPrice;
        this.payStatus = payStatus ? "결제 완료" : "결제 실패";
    }

    public static CoinOrderResponse of(CoinOrder coinOrder) {
        return CoinOrderResponse.builder()
                .orderId(coinOrder.getOrderId())
                .tid(coinOrder.getTid())
                .coinId(coinOrder.getCoin().getCoinId())
                .coinName(coinOrder.getCoin().getCoinName())
                .coinImage(coinOrder.getCoin().getCoinImage())
                .coinCount(coinOrder.getCoinCount())
                .totalPrice(coinOrder.getTotalPrice())
                .payStatus(coinOrder.getPayStatus())
                .build();
    }

}