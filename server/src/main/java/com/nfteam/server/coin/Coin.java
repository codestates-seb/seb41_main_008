package com.nfteam.server.coin;

import com.nfteam.server.item.entity.ItemPrice;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "trade_price", nullable = false, length = 400)
    private double tradePrice;

    @OneToMany(mappedBy = "coin")
    private List<ItemPrice> itemPrices = new ArrayList<>();

    protected Coin() {
    }

    @Builder
    public Coin(String coinName, double tradePrice) {
        this.coinName = coinName;
        this.tradePrice = tradePrice;
    }

    public void changeCoinName(final String coinName) {
        this.coinName = coinName;
    }

    public void changeTradePrice(final double tradePrice) {
        this.tradePrice = tradePrice;
    }
}
