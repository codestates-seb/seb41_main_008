package com.nfteam.server.dto.response.coin;

import com.nfteam.server.domain.coin.entity.CoinMemberRel;
import com.nfteam.server.domain.coin.entity.CoinType;
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
    private String coinImage;

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
        this.coinImage = findCoinImage(coinId);
    }

    public static MemberCoinResponse of(CoinMemberRel rel) {
        return MemberCoinResponse.builder()
                .memberId(rel.getMember().getMemberId())
                .nickname(rel.getMember().getNickname())
                .coinId(rel.getCoin().getCoinId())
                .coinCount(rel.getCoinCount())
                .build();
    }

    /**
     * TODO: 현재 다루는 코인이 5개 밖에 없어서 하드코딩, 추 후 확장 시 엔티티 설계 변경 후 디비 관리 필요.
     */
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

    private String findCoinImage(Long coinId) {
        switch (String.valueOf(coinId)) {
            case "1":
                return "https://nfteam-dev-img.s3.ap-northeast-2.amazonaws.com/solana.svg";
            case "2":
                return "https://nfteam-dev-img.s3.ap-northeast-2.amazonaws.com/btc.svg";
            case "3":
                return "https://nfteam-dev-img.s3.ap-northeast-2.amazonaws.com/doge.svg";
            case "4":
                return "https://nfteam-dev-img.s3.ap-northeast-2.amazonaws.com/eth.svg";
            case "5":
                return "https://nfteam-dev-img.s3.ap-northeast-2.amazonaws.com/etc.svg";
            default:
                throw new CoinNotFoundException(coinId);
        }
    }

    @Override
    public int compareTo(MemberCoinResponse o) {
        return Double.compare(this.coinId, o.coinId);
    }
}