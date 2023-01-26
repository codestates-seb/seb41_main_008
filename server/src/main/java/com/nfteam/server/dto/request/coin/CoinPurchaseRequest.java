package com.nfteam.server.dto.request.coin;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CoinPurchaseRequest {

    @NotNull(message = "코인 정보는 필수 값 입니다.")
    private Long coinId;

    @NotNull(message = "구매 할 코인 갯수를 입력해주세요.")
    private Double coinCount;

    @NotNull(message = "최종 결제 가격을 입력해주세요.")
    private Double totalPrice;

}