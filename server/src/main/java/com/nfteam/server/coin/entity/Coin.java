package com.nfteam.server.coin.entity;

import com.nfteam.server.common.audit.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "coin")
public class Coin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_id")
    private Long coinId;

    @Column(name = "coin_name", nullable = false, unique = true, length = 100)
    private String coinName;

    @Column(name = "withdraw_fee", nullable = false, length = 400)
    private Double withdrawFee;

    protected Coin() {
    }

    public Coin(Long coinId) {
        this.coinId = coinId;
    }

    public Coin(String coinName, Double withdrawFee) {
        this.coinName = coinName;
        this.withdrawFee = withdrawFee;
    }

    //@Builder
    public Coin(Long coinId, String coinName, Double withdrawFee) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.withdrawFee = withdrawFee;
    }

    public void changeCoinName(String coinName) {
        this.coinName = coinName;
    }

    public void changeWithdrawFee(Double withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

}