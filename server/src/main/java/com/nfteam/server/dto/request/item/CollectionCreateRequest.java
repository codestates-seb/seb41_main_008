package com.nfteam.server.dto.request.item;

import com.nfteam.server.domain.item.entity.ItemCollection;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CollectionCreateRequest {

    @NotNull(message = "해당 컬렉션의 코인 정보가 없습니다.")
    private String coinId;

    @NotNull(message = "해당 컬렉션의 이름 정보가 없습니다.")
    private String name;

    @NotNull(message = "해당 컬렉션의 설명 정보가 없습니다.")
    private String description;

    @NotNull(message = "해당 컬렉션의 로고 이미지가 없습니다.")
    private String logoImgName;

    @NotNull(message = "해당 컬렉션의 배너 이미지가 없습니다.")
    private String bannerImgName;

    private CollectionCreateRequest() {
    }

    public ItemCollection toCollection() {
        return ItemCollection.builder()
                .collectionName(name)
                .description(description)
                .logoImgName(logoImgName)
                .bannerImgName(bannerImgName)
                .build();
    }

}