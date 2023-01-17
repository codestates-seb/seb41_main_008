package com.nfteam.server.dto.response.item;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class UserCollectionResponse {
    // 유저 마이페이지 컬렉션 리스트

    // 컬렉션 기본 정보
    private Long collectionId;
    private String collectionName;
    private String description;
    private String logoImgName;
    private String bannerImgName;
    private String createdDate;

    // 컬렉션 코인 정보
    private Long coinId;
    private String coinName;

    @Builder
    public UserCollectionResponse(Long collectionId,
                                  String collectionName,
                                  String description,
                                  String logoImgName,
                                  String bannerImgName,
                                  LocalDateTime createdDate,
                                  Long coinId,
                                  String coinName) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.description = description;
        this.logoImgName = logoImgName;
        this.bannerImgName = bannerImgName;
        this.createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdDate);
        this.coinId = coinId;
        this.coinName = coinName;
    }
}
