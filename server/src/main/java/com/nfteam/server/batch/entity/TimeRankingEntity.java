package com.nfteam.server.batch.entity;

import com.nfteam.server.common.audit.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "time_rank")
public class TimeRankingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long rankId;

    @Column(name = "rank_time_criteria")
    private Integer rankCriteria;

    @Column(name = "rank_string", length = 500)
    private String rankString = "";

    public TimeRankingEntity() {
    }

    public void addString(Long num) {
        this.rankString += num;
        this.rankString += ",";
    }

    public void updateCriteria(Integer rankCriteria) {
        this.rankCriteria = rankCriteria;
    }

    public void updateRank(String rank) {
        this.rankString = rank;
    }

}