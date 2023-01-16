package com.nfteam.server.dto.request.item;

import com.nfteam.server.item.entity.Item;
import lombok.Getter;

@Getter
public class ItemPatchRequest {

    private String itemName;
    private String itemImgName;
    private String itemDescription;
    private Boolean onSale;
    private String itemPrice;

    public Item toItem() {
        return Item.builder()
                .itemName(itemName)
                .itemImageName(itemImgName)
                .itemDescription(itemDescription)
                .onSale(onSale)
                .itemPrice(Double.parseDouble(itemPrice))
                .build();
    }

}
