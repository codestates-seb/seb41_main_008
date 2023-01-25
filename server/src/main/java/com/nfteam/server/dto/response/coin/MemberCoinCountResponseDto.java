package com.nfteam.server.dto.response.coin;

import com.nfteam.server.coin.entity.Coin;
import com.nfteam.server.dto.response.member.MemberResponseDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberCoinCountResponseDto {
    private CoinMemberRelResponse coinMemberRelResponse;

    private List<MemberResponseDto> member;

    private List<CoinMemberRelResponse> memberCoins;

    public MemberCoinCountResponseDto(MemberResponseDto memberResponseDto, List<MemberResponseDto> member, List<CoinMemberRelResponse> memberCoins){
        this.member=member;
        this.memberCoins=memberCoins.stream()
                .map(i->CoinMemberRelResponse.of(i))
                .collect(Collectors.toList());
    }
}
