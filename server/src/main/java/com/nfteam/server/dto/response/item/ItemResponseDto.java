package com.nfteam.server.dto.response.item;

import com.nfteam.server.item.entity.ItemCollection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemResponseDto {

    private Long itemId;
    private Long collectionId;
    private String collectionName;
    private String ownerId;
    private String ownerName;
    private String itemName;
    private String itemImageName;
    private boolean onSale;
    private Double coinCount;

    @Builder
    public ItemResponseDto(Long itemId,
                           Long ownerId, String ownerName,
                           String itemName, String itemImageName,
                           boolean onSale, Double coinCount) {
        this.itemId = itemId;
        this.ownerId = String.valueOf(ownerId);
        this.ownerName = ownerName;
        this.itemName = itemName;
        this.itemImageName = itemImageName;
        this.onSale = onSale;
        this.coinCount = coinCount;
    }

    public void addCollectionInfo(ItemCollection collection){
        this.collectionId = collection.getCollectionId();
        this.collectionName = collection.getCollectionName();
    }
}
