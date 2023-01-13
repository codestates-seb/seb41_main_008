package com.nfteam.server.dto.request.item;

import lombok.Getter;

@Getter
public class ItemPatchRequest {

    private String itemName;
    private String itemImgName;
    private Boolean onSale;
    private String itemPrice;

}
