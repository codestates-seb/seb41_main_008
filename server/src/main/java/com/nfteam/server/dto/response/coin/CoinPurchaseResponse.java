package com.nfteam.server.dto.response.coin;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
public class CoinPurchaseResponse {

    private String tid;
    private String next_redirect_pc_url;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String created_at;

}