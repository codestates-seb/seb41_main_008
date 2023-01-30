package com.nfteam.server.dto.response.coin;

import com.nfteam.server.domain.coin.entity.CoinOrder;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CoinOrderResponse {

    // 주문자 정보
    private Long buyerId;
    private String nickname;

    // 주문 정보
    private Long orderId;
    private String tid;
    private Long coinId;
    private String coinName;
    private String coinImage;
    private Double coinCount;
    private Double totalPrice;
    private String payStatus;
    private String approvedAt;

    @Builder
    public CoinOrderResponse(Long buyerId,
                             String nickname,
                             Long orderId,
                             String tid,
                             Long coinId,
                             String coinName,
                             String coinImage,
                             Double coinCount,
                             Double totalPrice,
                             Boolean payStatus,
                             LocalDateTime approvedAt) {
        this.buyerId = buyerId;
        this.nickname = nickname;
        this.orderId = orderId;
        this.tid = tid;
        this.coinId = coinId;
        this.coinName = coinName;
        this.coinImage = coinImage;
        this.coinCount = coinCount;
        this.totalPrice = totalPrice;
        this.payStatus = payStatus ? "결제 완료" : "결제 실패";
        this.approvedAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(approvedAt);
    }

    public static CoinOrderResponse of(CoinOrder coinOrder) {
        return CoinOrderResponse.builder()
                .buyerId(coinOrder.getBuyer().getMemberId())
                .nickname(coinOrder.getBuyer().getNickname())
                .orderId(coinOrder.getOrderId())
                .tid(coinOrder.getTid())
                .coinId(coinOrder.getCoin().getCoinId())
                .coinName(coinOrder.getCoin().getCoinName())
                .coinImage(coinOrder.getCoin().getCoinImage())
                .coinCount(coinOrder.getCoinCount())
                .totalPrice(coinOrder.getTotalPrice())
                .payStatus(coinOrder.getPayStatus())
                .approvedAt(coinOrder.getModifiedDate())
                .build();
    }

}