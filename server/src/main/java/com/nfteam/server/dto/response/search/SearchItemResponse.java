package com.nfteam.server.dto.response.search;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchItemResponse {

    // 아이템 소속 컬렉션 정보
    private Long collectionId;
    private String collectionName;

    // 거래 가능 코인 정보
    private Long coinId;
    private String coinName;

    // 아이템 정보
    private Long itemId;
    private String itemName;
    private String itemImageName;
    private Boolean onSale;
    private Double itemPrice; // 코인 갯수(가격)

    @Builder
    @QueryProjection
    public SearchItemResponse(Long collectionId,
                              String collectionName,
                              Long coinId,
                              String coinName,
                              Long itemId,
                              String itemName,
                              String itemImageName,
                              Boolean onSale,
                              Double itemPrice) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.coinId = coinId;
        this.coinName = coinName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageName = itemImageName;
        this.onSale = onSale;
        this.itemPrice = itemPrice;
    }

}