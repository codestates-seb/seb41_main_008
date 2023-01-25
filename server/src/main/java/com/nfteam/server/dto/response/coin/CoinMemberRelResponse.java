package com.nfteam.server.dto.response.coin;

import com.amazonaws.services.ec2.model.CpuOptionsRequest;
import com.nfteam.server.coin.entity.Coin;
import com.nfteam.server.coin.entity.CoinMemberRel;
import com.nfteam.server.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CoinMemberRelResponse {
    private Long relId;
    private Long coinId;
    private Member member;
    private Coin coin;
    private Double coinCount;

    @Builder
    public CoinMemberRelResponse(Long relId,
                                 Long coinId,
                                 Member member,
                                 Coin coin,
                                 Double coinCount){
        this.relId=relId;
        this.coinId=coinId;
        this.member=member;
        this.coin=coin;
        this.coinCount=coinCount;

    }

    public static CoinMemberRelResponse of(CoinMemberRel coinMemberRel) {
        return CoinMemberRelResponse.builder()
                .coin(coinMemberRel.getCoin())
                .coinCount(coinMemberRel.getCoinCount())
                .member(coinMemberRel.getMember())
                .build();
    }


}
