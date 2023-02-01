package com.nfteam.server.dto.response.coin;

import com.nfteam.server.domain.coin.entity.CoinMemberRel;
import com.nfteam.server.domain.member.entity.Member;
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
    public MemberCoinResponse(Long memberId, String nickname, Long coinId, String coinName, Double coinCount, String coinImage) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.coinId = coinId;
        this.coinName = coinName;
        this.coinCount = Double.parseDouble(String.format("%.2f", coinCount));
        this.coinImage = coinImage;
    }

    public static MemberCoinResponse of(CoinMemberRel rel) {
        return MemberCoinResponse.builder()
                .memberId(rel.getMember().getMemberId())
                .nickname(rel.getMember().getNickname())
                .coinId(rel.getCoin().getCoinId())
                .coinName(rel.getCoin().getCoinName())
                .coinImage(rel.getCoin().getCoinImage())
                .coinCount(rel.getCoinCount())
                .build();
    }

    public static MemberCoinResponse ofMember(Member member) {
        return MemberCoinResponse.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .coinId(0L)
                .coinName("")
                .coinImage("")
                .coinCount(0.0)
                .build();
    }

    @Override
    public int compareTo(MemberCoinResponse o) {
        return Double.compare(this.coinId, o.coinId);
    }

}