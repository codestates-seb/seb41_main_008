package com.nfteam.server.batch.entity;

import com.nfteam.server.common.audit.BaseEntity;
import lombok.Builder;
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
    public Ranking6H(Long rank1, Long rank2, Long rank3, Long rank4, Long rank5, Long rank6, Long rank7, Long rank8, Long rank9, Long rank10, Long rank11, Long rank12, Long rank13, Long rank14, Long rank15) {
        this.rank1 = rank1;
        this.rank2 = rank2;
        this.rank3 = rank3;
        this.rank4 = rank4;
        this.rank5 = rank5;
        this.rank6 = rank6;
        this.rank7 = rank7;
        this.rank8 = rank8;
        this.rank9 = rank9;
        this.rank10 = rank10;
        this.rank11 = rank11;
        this.rank12 = rank12;
        this.rank13 = rank13;
        this.rank14 = rank14;
        this.rank15 = rank15;
    }

}