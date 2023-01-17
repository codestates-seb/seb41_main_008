package com.nfteam.server.dto.request.transaction;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
public class TransActionCreateRequest {

    @NotNull(message = "해당 아이템의 컬렉션 정보가 없습니다.")
    private String collectionId;

    @NotNull(message = "해당 거래 아이템의 아이템 정보가 없습니다.")
    private String itemId;

    @NotNull(message = "해당 거래의 판매자 정보가 없습니다.")
    private String sellerId;

    @NotNull(message = "해당 거래의 코인 정보가 없습니다.")
    private String coinId;

    @NotNull(message = "해당 거래의 거래가격 정보가 없습니다.")
    @Range(min = 0, max = 9999, message = "거래 가격 범위가 올바르지 않습니다.")
    private String transPrice;

    private TransActionCreateRequest() {
    }

}
