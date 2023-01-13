package com.nfteam.server.dto.response.item;

import com.nfteam.server.item.entity.ItemCollection;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemResponse {

    // 컬렉션 정보
    private Long collectionId;
    private String collectionName;

    // 코인 정보
    private Long coinId;
    private String coinName;
    private Double withdrawlFee;

    // 아이템 현재 소유자 정보
    private Long ownerId;
    private String ownerName;

    // 아이템 정보
    private Long itemId;
    private String itemName;
    private String itemImageName;
    private Boolean onSale;
    private Double itemPrice; // 코인 갯수

    // 거래 가격 히스토리
    private List<ItemPriceHistoryResponse> itemPriceHistory;

    // 거래 기록 히스토리
    private List<ItemTransHistoryResponse> itemTransHistory;

    @Builder
    public ItemResponse(Long collectionId, String collectionName,
                        Long coinId, String coinName,
                        Long ownerId, String ownerName,
                        Long itemId, String itemName,
                        String itemImageName, Boolean onSale, Double itemPrice) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.coinId = coinId;
        this.coinName = coinName;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageName = itemImageName;
        this.onSale = onSale;
        this.itemPrice = itemPrice;
    }

    public void addCollectionInfo(ItemCollection collection) {
        this.collectionId = collection.getCollectionId();
        this.collectionName = collection.getCollectionName();
    }
}
