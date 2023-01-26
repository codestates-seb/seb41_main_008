package com.nfteam.server.dto.request.cart;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class CartPurchaseRequest {

    @NotNull(message = "카트 번호는 필수 값 입니다.")
    private Long cartId;

    private List<Long> itemIdList;

    private Double totalPrice;

}