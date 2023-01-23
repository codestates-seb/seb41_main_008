package com.nfteam.server.batch.entity;

import com.nfteam.server.common.audit.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "ranking_sol")
public class RankingSOL extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_sol_id")
    private Long rankId;

    @Column(name = "rank_sol_string", length = 500)
    private String rankString = "";

    public RankingSOL() {
    }

    public void addString(Long num) {
        this.rankString += num;
        this.rankString += ",";
    }

    public void changeString(String rank) {
        this.rankString = rank;
    }

}