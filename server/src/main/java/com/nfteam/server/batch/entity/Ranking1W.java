package com.nfteam.server.batch.entity;

import com.nfteam.server.common.audit.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "ranking_1w")
public class Ranking1W extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_1w_id")
    private Long rankId;

    @Column(name = "rank_1w_string", length = 500)
    private String rankString = "";

    public Ranking1W() {
    }

    public void addString(Long num) {
        this.rankString += num;
        this.rankString += ",";
    }

    public void changeString(String rank) {
        this.rankString = rank;
    }

}