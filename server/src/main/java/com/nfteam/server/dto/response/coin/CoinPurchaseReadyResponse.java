package com.nfteam.server.dto.response.coin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinPurchaseReadyResponse {

    private String tid;
    @JsonProperty("next_redirect_pc_url")
    private String nextRedirectPcUrl;
    @JsonProperty("created_at")
    private String createdAt;

}