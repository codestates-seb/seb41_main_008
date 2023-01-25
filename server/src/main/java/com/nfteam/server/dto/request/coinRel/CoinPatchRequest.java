package com.nfteam.server.dto.request.coinRel;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CoinPatchRequest {

    @NotNull(message = "coinName can't be null")
    private String coinName;

    private Double increase;




}
