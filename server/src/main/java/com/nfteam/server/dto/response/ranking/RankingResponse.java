package com.nfteam.server.dto.response.ranking;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RankingResponse {

    // 컬렉션 기본 정보
    private Long collectionId;
    private String collectionName;
    private String logoImgName;

    // 관련 코인 정보
    private Long coinId;
    private String coinName;

    // 메타 정보
    private Double totalVolume; // 총 코인 갯수(가격) 합
    private Double highestPrice; // 최고가

    @Builder
    public RankingResponse(Long collectionId,
                           String collectionName,
                           String logoImgName,
                           Long coinId,
                           String coinName,
                           Double totalVolume,
                           Double highestPrice) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.logoImgName = logoImgName;
        this.coinId = coinId;
        this.coinName = coinName;
        this.totalVolume = totalVolume;
        this.highestPrice = highestPrice;
    }

}
