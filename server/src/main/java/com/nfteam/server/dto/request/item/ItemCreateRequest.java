package com.nfteam.server.dto.request.item;

import com.nfteam.server.domain.item.entity.Item;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ItemCreateRequest {

    @NotNull(message = "해당 아이템 소속 Collection 정보가 없습니다.")
    private String itemCollectionId;

    @NotNull(message = "해당 아이템 이름 정보가 없습니다.")
    private String itemName;

    @NotNull(message = "해당 아이템 이미지 정보가 없습니다.")
    private String itemImgName;

    @NotNull(message = "해당 아이템 설명 정보가 없습니다.")
    private String itemDescription;

    private ItemCreateRequest() {
    }

    public Item toItem() {
        return Item.builder()
                .itemName(itemName)
                .itemImageName(itemImgName)
                .itemDescription(itemDescription)
                .onSale(false)
                .itemPrice(0.0)
                .build();
    }

}