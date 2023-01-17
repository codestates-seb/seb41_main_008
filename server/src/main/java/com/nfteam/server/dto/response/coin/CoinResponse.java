package com.nfteam.server.dto.response.coin;


import com.nfteam.server.coin.entity.Coin;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CoinResponse {

    private Long coinId;
    private String coinName;
    private Double withdrawlFee;

    @Builder
    public CoinResponse(Long coinId, String coinName, Double withdrawlFee) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.withdrawlFee = withdrawlFee;
    }

    public static CoinResponse of(Coin coin) {
        return CoinResponse.builder()
                .coinId(coin.getCoinId())
                .coinName(coin.getCoinName())
                .withdrawlFee(coin.getWithdrawlFee())
                .build();
    }

}