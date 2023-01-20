package com.nfteam.server.batch.entity;

import com.nfteam.server.common.audit.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "ranking_btc")
public class RankingBTC extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_btc_id")
    private Long rankId;

    @Column(name = "rank_string")
    private String rankString = "";

    public RankingBTC() {
    }

    public void addString(Long num) {
        this.rankString += num;
        this.rankString += ",";
    }

}