package com.nfteam.server.batch.entity;

import com.nfteam.server.common.audit.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "ranking_doge")
public class RankingDOGE extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_doge_id")
    private Long rankId;

    @Column(name = "rank_string")
    private String rankString = "";

    public RankingDOGE() {
    }

    public void addString(Long num) {
        this.rankString += num;
        this.rankString += ",";
    }

}