package com.nfteam.server.dto.request.item;

import com.nfteam.server.item.entity.Item;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ItemCreateRequest {

    @NotNull(message = "해당 아이템 소속 Collection 정보가 없습니다.")
    private String itemCollectionId;

    @NotNull(message = "해당 아이템 이름 정보가 없습니다.")
    private String itemName;

    @NotNull(message = "해당 아이템 가격 정보가 없습니다.")
    private String itemPrice;

    private ItemCreateRequest() {
    }

    public Item toItem() {
        return Item.builder()
                .itemName(itemName)
                .itemPrice(Long.parseLong(itemPrice))
                .build();
    }
}