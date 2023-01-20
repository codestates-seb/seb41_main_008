package com.nfteam.server.dto.response.cart;

import com.nfteam.server.dto.response.item.ItemResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartItemResponse {

    // 아이템 소속 컬렉션 정보
    private Long collectionId;
    private String collectionName;

    // 아이템 현재 소유자 정보
    private Long ownerId;
    private String ownerName;

    // 거래 가능 코인 정보
    private Long coinId;
    private String coinName;
    private Double withdrawFee;

    // 아이템 정보
    private Long itemId;
    private String itemName;
    private String itemImageName;
    private Boolean onSale;
    private Double itemPrice; // 코인 갯수(가격)

    @Builder
    public CartItemResponse(Long collectionId,
                            String collectionName,
                            Long ownerId,
                            String ownerName,
                            Long coinId,
                            String coinName,
                            Double withdrawFee,
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
        this.withdrawFee = withdrawFee;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageName = itemImageName;
        this.onSale = onSale;
        this.itemPrice = itemPrice;
    }

    public static CartItemResponse of(ItemResponse itemResponse) {
        return CartItemResponse.builder()
                .collectionId(itemResponse.getCollectionId())
                .collectionName(itemResponse.getCollectionName())
                .ownerId(itemResponse.getOwnerId())
                .ownerName(itemResponse.getOwnerName())
                .coinId(itemResponse.getCoinId())
                .coinName(itemResponse.getCoinName())
                .withdrawFee(itemResponse.getWithdrawFee())
                .itemId(itemResponse.getItemId())
                .itemName(itemResponse.getItemName())
                .itemImageName(itemResponse.getItemImageName())
                .onSale(itemResponse.getOnSale())
                .itemPrice(itemResponse.getItemPrice())
                .build();
    }

}