package com.nfteam.server.coin;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "coin")
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_id")
    private Long coinId;

    @Column(name = "coin_name", nullable = false, length = 100)
    private String coinName;

    @Column(name = "current_price", nullable = false, length = 400)
    private Long currentPrice;

    protected Coin() {
    }

    public Coin(Long coinId) {
        this.coinId = coinId;
    }

    @Builder
    public Coin(String coinName, Long currentPrice) {
        this.coinName = coinName;
        this.currentPrice = currentPrice;
    }
}
