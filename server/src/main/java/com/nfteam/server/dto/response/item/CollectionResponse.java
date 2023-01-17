package com.nfteam.server.dto.response.item;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class CollectionResponse {

    // 컬렉션 기본 정보
    private Long collectionId;
    private String collectionName;
    private String description;
    private String logoImgName;
    private String bannerImgName;
    private String createdDate;

    // 컬렉션 소유자 정보
    private Long ownerId;
    private String ownerName;

    // 컬렉션 코인 정보
    private Long coinId;
    private String coinName;

    // 컬렉션 소속 아이템리스트 메타정보
    private Integer itemCount; // 아이템 갯수
    private Double totalVolume; // 총 코인 갯수(가격) 합
    private Double highestPrice; // 최고가
    private Double lowestPrice; // 최저가
    private Integer ownerCount; // 소유자 수

    // 소속 아이템 리스트
    private List<ItemResponse> itemList;

    @Builder
    public CollectionResponse(Long collectionId,
                              String collectionName,
                              String description,
                              String logoImgName,
                              String bannerImgName,
                              LocalDateTime createdDate,
                              Long ownerId,
                              String ownerName,
                              Long coinId,
                              String coinName) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.description = description;
        this.logoImgName = logoImgName;
        this.bannerImgName = bannerImgName;
        this.createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdDate);
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.coinId = coinId;
        this.coinName = coinName;
    }

    public void addMetaInfo(Integer itemCount,
                            Double totalVolume,
                            Double highestPrice,
                            Double lowestPrice,
                            Integer ownerCount) {
        this.itemCount = itemCount;
        this.totalVolume = totalVolume;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.ownerCount = ownerCount;
    }

    public void addItemResponseDtos(List<ItemResponse> items) {
        this.itemList = items;
    }
}
