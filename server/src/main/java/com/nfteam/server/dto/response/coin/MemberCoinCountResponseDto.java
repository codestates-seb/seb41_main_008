package com.nfteam.server.dto.response.coin;

import com.nfteam.server.coin.entity.Coin;
import com.nfteam.server.dto.response.member.MemberResponseDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberCoinCountResponseDto {
    private CoinMemberRelResponse coinMemberRelResponse;

    private List<Double> memberCoins;

    public MemberCoinCountResponseDto(CoinMemberRelResponse memberResponseDto,  List<Double> memberCoins){
        this.coinMemberRelResponse=memberResponseDto;
        this.memberCoins=memberCoins;
    }
}
