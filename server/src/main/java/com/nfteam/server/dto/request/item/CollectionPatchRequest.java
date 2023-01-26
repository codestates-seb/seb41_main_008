package com.nfteam.server.dto.request.item;

import com.nfteam.server.item.entity.ItemCollection;
import lombok.Getter;

@Getter
public class CollectionPatchRequest {

    private String name;
    private String description;
    private String logoImgName;
    private String bannerImgName;

    private CollectionPatchRequest() {
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
