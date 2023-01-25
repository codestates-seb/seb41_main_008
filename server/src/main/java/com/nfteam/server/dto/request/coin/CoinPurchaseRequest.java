package com.nfteam.server.dto.request.coin;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CoinPurchaseRequest {

    @NotNull(message = "코인 정보는 필수 값 입니다.")
    private Long coinId;
    private Double coinCount;
    private Double totalPrice;

}