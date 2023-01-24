package com.nfteam.server.dto.request.transaction;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
public class TransActionRequestItemInfo {

    @NotNull(message = "아이템 아이디 정보가 없습니다.")
    private Long itemId;

    @NotNull(message = "판매자 정보가 없습니다.")
    private Long sellerId;

    @NotNull(message = "아이템 거래 코인 정보가 없습니다.")
    private Long coinId;

    @NotNull(message = "거래가격 정보가 없습니다.")
    @Range(min = 0, max = 9999, message = "거래 가격은 0 ~ 9999 범위를 벗어날 수 없습니다.")
    private Double transPrice;

    private TransActionRequestItemInfo() {
    }

}