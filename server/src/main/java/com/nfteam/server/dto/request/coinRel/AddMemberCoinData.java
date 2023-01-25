package com.nfteam.server.dto.request.coinRel;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AddMemberCoinData {

    @NotNull(message = "coin name can't be null")
    private String coinName;

    @NotNull(message = "coin count can't be null")
    private Double coinCount;

}
