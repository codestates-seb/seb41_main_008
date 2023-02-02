package com.nfteam.server.domain.coin.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "coin_fee_history")
public class CoinFeeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_history_id")
    private Long coinHistoryId;

    @Column(name = "coin_name", nullable = false, length = 100)
    private String coinName;

    @Column(name = "withdraw_fee", nullable = false, length = 400)
    private Double withdrawFee;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    protected CoinFeeHistory() {
    }

    @Builder
    public CoinFeeHistory(String coinName, Double withdrawFee) {
        this.coinName = coinName;
        this.withdrawFee = withdrawFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoinFeeHistory that = (CoinFeeHistory) o;
        return coinHistoryId.equals(that.coinHistoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coinHistoryId);
    }

}