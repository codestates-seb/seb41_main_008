package com.nfteam.server.domain.coin.entity;

import com.nfteam.server.domain.audit.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

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

    @Column(name = "coin_image")
    private String coinImage;

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

    @Builder
    public Coin(Long coinId, String coinName, String coinImage, Double withdrawFee) {
        this.coinId = coinId;
        this.coinName = coinName;
        this.coinImage = coinImage;
        this.withdrawFee = withdrawFee;
    }

    public void changeWithdrawFee(Double withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public void changeCoinImage(String coinImage) {
        this.coinImage = coinImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coin coin = (Coin) o;
        return coinId.equals(coin.coinId) && coinName.equals(coin.coinName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coinId, coinName);
    }

}