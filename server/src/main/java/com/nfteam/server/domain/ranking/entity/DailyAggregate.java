package com.nfteam.server.domain.ranking.entity;

import com.nfteam.server.domain.audit.BaseEntity;
import com.nfteam.server.domain.coin.entity.Coin;
import com.nfteam.server.domain.item.entity.ItemCollection;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Entity
@Table(name = "daily_aggregate")
public class DailyAggregate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aggregate_id")
    private Long aggregateId;

    // 해당 컬렉션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id")
    private ItemCollection collection;

    // 해당 코인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private Coin coin;

    // 기준 날짜
    private LocalDate baseDate;

    // 총 거래량
    private Long totalTradingVolume;

    // 총 거래 가격
    private Double totalTradingPrice;

    protected DailyAggregate() {
    }

    @Builder
    public DailyAggregate(ItemCollection collection, Coin coin, LocalDate baseDate, Long totalTradingVolume, Double totalTradingPrice) {
        this.collection = collection;
        this.coin = coin;
        this.baseDate = baseDate;
        this.totalTradingVolume = totalTradingVolume;
        this.totalTradingPrice = totalTradingPrice;
    }

    public void addTotalVolume() {
        this.totalTradingVolume++;
    }

    public void addTotalTradingPrice(Double volume) {
        this.totalTradingPrice += volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyAggregate that = (DailyAggregate) o;
        return collection.equals(that.collection) && baseDate.equals(that.baseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection, baseDate);
    }
}
