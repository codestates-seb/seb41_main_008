package com.nfteam.server.coin.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public CoinFeeHistory(Long coinHistoryId) {
        this.coinHistoryId = coinHistoryId;
    }

    @Builder
    public CoinFeeHistory(String coinName, Double withdrawFee) {
        this.coinName = coinName;
        this.withdrawFee = withdrawFee;
    }

}