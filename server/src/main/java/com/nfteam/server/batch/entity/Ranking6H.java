package com.nfteam.server.batch.entity;

import com.nfteam.server.common.audit.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "ranking_6h")
public class Ranking6H extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_6h_id")
    private Long rankId;

    @Column(name = "rank_string")
    private String rankString = "";

    public Ranking6H() {
    }

    public void addString(Long num) {
        this.rankString += num;
        this.rankString += ",";
    }

}