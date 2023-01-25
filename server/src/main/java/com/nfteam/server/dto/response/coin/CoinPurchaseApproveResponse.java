package com.nfteam.server.dto.response.coin;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String created_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String approved_at;
    private String payload;

}