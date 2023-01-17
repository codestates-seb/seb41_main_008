package com.nfteam.server.coin.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "coin")
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_id")
    private Long coinId;

    @Column(name = "coin_name", nullable = false, unique = true, length = 100)
    private String coinName;

    @Column(name = "withdrwal_fee", length = 400)
    private double withdrawlFee;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    protected Coin() {
    }

    public Coin(Long coinId) {
        this.coinId = coinId;
    }

    @Builder
    public Coin(String coinName, double withdrawlFee) {
        this.coinName = coinName;
        this.withdrawlFee = withdrawlFee;
    }
}
