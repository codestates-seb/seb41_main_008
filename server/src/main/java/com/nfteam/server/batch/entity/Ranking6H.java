package com.nfteam.server.batch.entity;

import com.nfteam.server.common.audit.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "ranking_6h")
public class Ranking6H extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_6h_id")
    private Long rankId;
    private Long rank1;
    private Long rank2;
    private Long rank3;
    private Long rank4;
    private Long rank5;
    private Long rank6;
    private Long rank7;
    private Long rank8;
    private Long rank9;
    private Long rank10;
    private Long rank11;
    private Long rank12;
    private Long rank13;
    private Long rank14;
    private Long rank15;

    protected Ranking6H() {
    }

    @Builder
    public Ranking6H(List<Long> rankList) {
        this.rank1 = rankList.get(0) == null ? 0 : rankList.get(0);
        this.rank2 = rankList.get(1) == null ? 0 : rankList.get(1);
        this.rank3 = rankList.get(2) == null ? 0 : rankList.get(2);
        this.rank4 = rankList.get(3) == null ? 0 : rankList.get(3);
        this.rank5 = rankList.get(4) == null ? 0 : rankList.get(4);
        this.rank6 = rankList.get(5) == null ? 0 : rankList.get(5);
        this.rank7 = rankList.get(6) == null ? 0 : rankList.get(6);
        this.rank8 = rankList.get(7) == null ? 0 : rankList.get(7);
        this.rank9 = rankList.get(8) == null ? 0 : rankList.get(8);
        this.rank10 = rankList.get(9) == null ? 0 : rankList.get(9);
        this.rank11 = rankList.get(10) == null ? 0 : rankList.get(10);
        this.rank12 = rankList.get(11) == null ? 0 : rankList.get(11);
        this.rank13 = rankList.get(12) == null ? 0 : rankList.get(12);
        this.rank14 = rankList.get(13) == null ? 0 : rankList.get(13);
        this.rank15 = rankList.get(14) == null ? 0 : rankList.get(14);
    }

}