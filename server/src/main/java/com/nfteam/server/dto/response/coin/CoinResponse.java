package com.nfteam.server.dto.response.coin;

import com.nfteam.server.coin.entity.Coin;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CoinResponse {

    private Long coinId;
    private String coinName;
    private Double withdrawFee;

    @Builder
    public CoinResponse(Long coinId, String coinName, Double withdrawFee) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.withdrawFee = withdrawFee;
    }

    public static CoinResponse of(Coin coin) {
        return CoinResponse.builder()
                .coinId(coin.getCoinId())
                .coinName(coin.getCoinName())
                .withdrawFee(coin.getWithdrawFee())
                .build();
    }

}