package com.nfteam.server.dto.response.member;

import com.nfteam.server.dto.response.item.ItemResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberItemResponse {

    // 아이템 소유자 정보
    private Long ownerId;
    private String ownerName;

    // 아이템 소속 컬렉션 정보
    private Long collectionId;
    private String collectionName;

    // 거래 가능 코인 정보
    private Long coinId;
    private String coinName;
    private Double coinWithdrawFee;

    // 아이템 정보
    private Long itemId;
    private String itemName;
    private String itemImageName;
    private String itemDescription;
    private Boolean onSale;
    private Double itemPrice; // 코인 갯수(가격)

    @Builder
    public MemberItemResponse(Long ownerId,
                              String ownerName,
                              Long collectionId,
                              String collectionName,
                              Long coinId,
                              String coinName,
                              Double coinWithdrawFee,
                              Long itemId,
                              String itemName,
                              String itemImageName,
                              String itemDescription,
                              Boolean onSale,
                              Double itemPrice) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.coinId = coinId;
        this.coinName = coinName;
        this.coinWithdrawFee = coinWithdrawFee;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageName = itemImageName;
        this.itemDescription = itemDescription;
        this.onSale = onSale;
        this.itemPrice = itemPrice;
    }

    public static MemberItemResponse of(ItemResponse itemResponse) {
        return MemberItemResponse.builder()
                .ownerId(itemResponse.getOwnerId())
                .ownerName(itemResponse.getOwnerName())
                .collectionId(itemResponse.getCollectionId())
                .collectionName(itemResponse.getCollectionName())
                .coinId(itemResponse.getCoinId())
                .coinName(itemResponse.getCoinName())
                .coinWithdrawFee(itemResponse.getWithdrawFee())
                .itemId(itemResponse.getItemId())
                .itemName(itemResponse.getItemName())
                .itemImageName(itemResponse.getItemImageName())
                .itemDescription(itemResponse.getItemDescription())
                .onSale(itemResponse.getOnSale())
                .itemPrice(itemResponse.getItemPrice())
                .build();
    }

}