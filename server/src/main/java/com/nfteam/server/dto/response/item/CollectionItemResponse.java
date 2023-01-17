package com.nfteam.server.dto.response.item;

import com.nfteam.server.item.entity.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CollectionItemResponse {

    // 아이템 현재 소유자 정보
    private Long ownerId;
    private String ownerName;

    // 아이템 정보
    private Long itemId;
    private String itemName;
    private String itemImageName;
    private String itemDescription;
    private Boolean onSale;
    private Double itemPrice; // 코인 갯수(가격)

    @Builder
    public CollectionItemResponse(Long ownerId,
                                  String ownerName,
                                  Long itemId,
                                  String itemName,
                                  String itemImageName,
                                  String itemDescription,
                                  Boolean onSale,
                                  Double itemPrice) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageName = itemImageName;
        this.itemDescription = itemDescription;
        this.onSale = onSale;
        this.itemPrice = itemPrice;
    }

    public static CollectionItemResponse of(Item item) {
        return CollectionItemResponse.builder()
                .ownerId(item.getMember().getMemberId())
                .ownerName(item.getMember().getNickname())
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .itemImageName(item.getItemImageName())
                .itemDescription(item.getItemDescription())
                .onSale(item.getOnSale())
                .itemPrice(item.getItemPrice())
                .build();
    }
}
