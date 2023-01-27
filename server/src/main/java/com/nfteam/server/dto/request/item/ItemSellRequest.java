package com.nfteam.server.dto.request.item;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class ItemSellRequest {

    @NotNull(message = "가격은 필수값입니다.")
    @Range(min = 0, max = 9999, message = "가격은 0 ~ 9999 이내로 입력해주세요.")
    private String itemPrice;

    // todo: 판매 마감일 입력 받을 지 결정
    private LocalDateTime saleEnds;

}