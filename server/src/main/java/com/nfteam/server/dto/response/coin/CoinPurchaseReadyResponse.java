package com.nfteam.server.dto.response.coin;


import lombok.Getter;

@Getter
public class CoinPurchaseReadyResponse {

    private String tid;
    private String next_redirect_pc_url;
    private String created_at;

}