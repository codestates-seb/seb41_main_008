package com.nfteam.server.domain.ranking.batch.entity;

import com.nfteam.server.domain.audit.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "coin_rank")
public class CoinRankingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long rankId;

    @Column(name = "rank_coin_id")
    private Long coinId;

    @Column(name = "rank_string", length = 500)
    private String rankString = "";

    public CoinRankingEntity() {
    }

    public void addString(Long num) {
        this.rankString += num;
        this.rankString += ",";
    }

    public void updateCriteria(Long coinId) {
        this.coinId = coinId;
    }

    public void updateRank(String rank) {
        this.rankString = rank;
    }

}