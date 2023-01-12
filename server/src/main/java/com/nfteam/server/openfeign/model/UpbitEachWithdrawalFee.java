package com.nfteam.server.openfeign.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpbitEachWithdrawalFee {

    private String currency;
    private Double withdrawFee;

}
