package com.nfteam.server.dto.request.cart;

import java.util.List;

import lombok.Getter;

@Getter
public class CartPurchaseRequest {

    // 서로 다른 코인의 경우 한 장바구니에 같이 담기지 못한다.
    private Long cartId;
    private List<Long> itemIdList;
    private Double totalPrice;

}
