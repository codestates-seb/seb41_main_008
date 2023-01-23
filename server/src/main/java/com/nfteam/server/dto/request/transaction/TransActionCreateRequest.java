package com.nfteam.server.dto.request.transaction;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class TransActionCreateRequest {

    @NotNull(message = "해당 거래의 장바구니 정보가 없습니다.")
    private Long cartId;

    private List<TransActionRequestItemInfo> itemInfo;

    private TransActionCreateRequest() {
    }

}