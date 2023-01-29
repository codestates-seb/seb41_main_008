package com.nfteam.server.dto.response.item;

import com.nfteam.server.domain.item.entity.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CollectionItemResponse implements Comparable<CollectionItemResponse> {

    // 현재 컬렉션 정보
    private Long collectionId;
    private String collectionName;

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

    // 코인정보
    private Long coinId;
    private String coinName;
    private String coinImage;

    @Builder
    public CollectionItemResponse(Long collectionId,
                                  String collectionName,
                                  Long ownerId,
                                  String ownerName,
                                  Long itemId,
                                  String itemName,
                                  String itemImageName,
                                  String itemDescription,
                                  Boolean onSale,
                                  Double itemPrice,
                                  Long coinId,
                                  String coinName,
                                  String coinImage) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageName = itemImageName;
        this.itemDescription = itemDescription;
        this.onSale = onSale;
        this.itemPrice = itemPrice;
        this.coinId = coinId;
        this.coinName = coinName;
        this.coinImage = coinImage;
    }

    public static CollectionItemResponse of(Item item) {
        return CollectionItemResponse.builder()
                .collectionId(item.getCollection().getCollectionId())
                .collectionName(item.getCollection().getCollectionName())
                .ownerId(item.getMember().getMemberId())
                .ownerName(item.getMember().getNickname())
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .itemImageName(item.getItemImageName())
                .itemDescription(item.getItemDescription())
                .onSale(item.getOnSale())
                .itemPrice(item.getItemPrice())
                .coinId(item.getCollection().getCoin().getCoinId())
                .coinName(item.getCollection().getCoin().getCoinName())
                .coinImage(item.getCollection().getCoin().getCoinImage())
                .build();
    }

    // 판매 상품 -> 미판매 상품 순
    // 낮은 가격 -> 높은 가격 순
    @Override
    public int compareTo(CollectionItemResponse o) {
        if (o.onSale == this.onSale) {
            return Double.compare(this.itemPrice, o.itemPrice);
        } else {
            return Boolean.compare(o.onSale, this.onSale);
        }
    }

}