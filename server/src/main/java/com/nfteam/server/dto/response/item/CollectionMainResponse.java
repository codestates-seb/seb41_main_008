package com.nfteam.server.dto.response.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CollectionMainResponse {

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
    private String coinImage;

    @Builder
    @QueryProjection
    public CollectionMainResponse(Long collectionId,
                                  String collectionName,
                                  String description,
                                  String logoImgName,
                                  String bannerImgName,
                                  LocalDateTime createdDate,
                                  Long ownerId,
                                  String ownerName,
                                  Long coinId,
                                  String coinName,
                                  String coinImage) {
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
        this.coinImage = coinImage;
    }

}