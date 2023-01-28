package com.nfteam.server.domain.coin.entity;

import com.nfteam.server.domain.audit.BaseEntity;
import com.nfteam.server.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "coin_member_rel")
public class CoinMemberRel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rel_id")
    private Long relId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private Coin coin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "coin_count")
    private Double coinCount;

    protected CoinMemberRel() {
    }

    @Builder
    public CoinMemberRel(Coin coin, Member member) {
        this.coin = coin;
        this.member = member;
        this.coinCount = 0.0;
    }

    public void addCoinCount(Double cnt) {
        this.coinCount += cnt;
    }

    public void minusCoinCount(Double cnt) {
        this.coinCount -= cnt;
    }

}