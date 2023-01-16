package com.nfteam.server.dto.response.item;

import com.nfteam.server.dto.response.coin.CoinResponse;
import com.nfteam.server.item.entity.ItemCollection;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemResponse {

    // 아이템 소속 컬렉션 정보
    private Long collectionId;
    private String collectionName;

    // 아이템 현재 소유자 정보
    private Long ownerId;
    private String ownerName;

    // 거래 가능 코인 정보
    private Long coinId;
    private String coinName;
    private Double withdrawlFee;

    // 아이템 정보
    private Long itemId;
    private String itemName;
    private String itemImageName;
    private Boolean onSale;
    private Double itemPrice; // 코인 갯수(가격)

    // 아이템 거래 히스토리
    private List<ItemTradeHistoryResponse> tradeHistory;

    // 아이템 가격 히스토리
    private List<ItemPriceHistoryResponse> priceHistory;

    @Builder
    @QueryProjection
    public ItemResponse(Long collectionId,
                        String collectionName,
                        Long ownerId,
                        String ownerName,
                        Long coinId,
                        String coinName,
                        Double withdrawlFee,
                        Long itemId,
                        String itemName,
                        String itemImageName,
                        Boolean onSale,
                        Double itemPrice) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.coinId = coinId;
        this.coinName = coinName;
        this.withdrawlFee = withdrawlFee;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageName = itemImageName;
        this.onSale = onSale;
        this.itemPrice = itemPrice;
    }

    public void addTradeHistory(List<ItemTradeHistoryResponse> tradeHistory) {
        this.tradeHistory = tradeHistory;
    }

    public void addPriceHistory(List<ItemPriceHistoryResponse> priceHistory) {
        this.priceHistory = priceHistory;
    }

}
