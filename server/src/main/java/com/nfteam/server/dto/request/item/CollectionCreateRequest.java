package com.nfteam.server.dto.request.item;

import com.nfteam.server.item.entity.ItemCollection;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CollectionCreateRequest {

    @NotNull(message = "해당 컬렉션의 이름 정보가 없습니다.")
    private String name;

    @NotNull(message = "해당 컬렉션의 설명 정보가 없습니다.")
    private String description;

    private String logoImgName;

    private String bannerImgName;

    private CollectionCreateRequest() {
    }

    public ItemCollection toCollection() {
        return ItemCollection.builder()
                .collectionName(name)
                .description(description)
                .bannerImgName(bannerImgName)
                .logoImgName(logoImgName)
                .build();
    }
}
