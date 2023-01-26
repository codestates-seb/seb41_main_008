package com.nfteam.server.dto.response.search;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchCollectionResponse {

    private Long collectionId;
    private String collectionName;
    private String logoImgName;
    private String bannerImgName;

    @Builder
    @QueryProjection
    public SearchCollectionResponse(Long collectionId,
                                    String collectionName,
                                    String logoImgName,
                                    String bannerImgName) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.logoImgName = logoImgName;
        this.bannerImgName = bannerImgName;
    }

}