package com.nfteam.server.dto.response.coin;

import com.nfteam.server.coin.entity.CoinMemberRel;
import com.nfteam.server.coin.entity.CoinType;
import com.nfteam.server.exception.coin.CoinNotFoundException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCoinResponse implements Comparable<MemberCoinResponse> {

    private Long memberId;
    private String nickname;
    private Long coinId;
    private String coinName;
    private Double coinCount;

    @Builder
    public MemberCoinResponse(Long memberId,
                              String nickname,
                              Long coinId,
                              Double coinCount) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.coinId = coinId;
        this.coinName = findCoinName(coinId);
        this.coinCount = coinCount;
    }

    public static MemberCoinResponse of(CoinMemberRel rel) {
        return MemberCoinResponse.builder()
                .memberId(rel.getMember().getMemberId())
                .nickname(rel.getMember().getNickname())
                .coinId(rel.getCoin().getCoinId())
                .coinCount(rel.getCoinCount())
                .build();
    }

    private String findCoinName(Long coinId) {
        switch (String.valueOf(coinId)) {
            case "1":
                return CoinType.SOL.name();
            case "2":
                return CoinType.BTC.name();
            case "3":
                return CoinType.DOGE.name();
            case "4":
                return CoinType.ETH.name();
            case "5":
                return CoinType.ETC.name();
            default:
                throw new CoinNotFoundException(coinId);
        }
    }

    @Override
    public int compareTo(MemberCoinResponse o) {
        return Double.compare(this.coinId, o.coinId);
    }
}