package com.nfteam.server.coin;

import com.nfteam.server.common.audit.BaseEntity;
import com.nfteam.server.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "coin_member_rel")
public class CoinMemberRel extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rel_id")
    private Long relId;

    @Column(name = "coin_count")
    private Double coinCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private Coin coin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected CoinMemberRel() {
    }

    @Builder
    public CoinMemberRel(Coin coin, Member member) {
        this.coin = coin;
        this.member = member;
    }

    public void addCoinCount(Double cnt) {
        this.coinCount += cnt;
    }

    public void minusCoinCount(Double cnt) {
        this.coinCount -= cnt;
    }
}
