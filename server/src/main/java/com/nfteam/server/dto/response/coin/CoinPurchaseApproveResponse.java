package com.nfteam.server.dto.response.coin;

import lombok.Getter;

@Getter
public class CoinPurchaseApproveResponse {

    private String aid;
    private String tid;
    private String cid;
    private String sid;
    private String partner_order_id;
    private String partner_user_id;
    private String payment_method_type;
    private String item_name;
    private String item_code;
    private int quantity;
    private String payload;
    private String created_at;
    private String approved_at;

}