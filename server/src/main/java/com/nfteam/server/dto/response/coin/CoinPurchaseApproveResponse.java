package com.nfteam.server.dto.response.coin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinPurchaseApproveResponse {

    private String aid;
    private String tid;
    private String cid;
    private String sid;
    private int quantity;
    private String payload;

    @JsonProperty("partner_order_id")
    private String partnerOrderId;
    @JsonProperty("partner_user_id")
    private String partnerUserId;
    @JsonProperty("payment_method_type")
    private String paymentMethodType;
    @JsonProperty("item_name")
    private String itemName;
    @JsonProperty("item_code")
    private String itemCode;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("approved_at")
    private String approvedAt;

}