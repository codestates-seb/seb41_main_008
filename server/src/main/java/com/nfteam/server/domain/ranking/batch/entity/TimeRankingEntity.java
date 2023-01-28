package com.nfteam.server.domain.ranking.batch.entity;

import com.nfteam.server.domain.audit.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@Table(name = "time_rank")
public class TimeRankingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long rankId;

    @Column(name = "rank_time_criteria")
    private String rankCriteria;

    @Column(name = "rank_string", length = 500)
    private String rankString = "";

    public TimeRankingEntity() {
    }

    public void addString(Long num) {
        this.rankString += num;
        this.rankString += ",";
    }

    public void updateCriteria(String rankCriteria) {
        this.rankCriteria = rankCriteria;
    }

    public void updateRank(String rank) {
        this.rankString = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeRankingEntity that = (TimeRankingEntity) o;
        return rankId.equals(that.rankId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rankId);
    }

}