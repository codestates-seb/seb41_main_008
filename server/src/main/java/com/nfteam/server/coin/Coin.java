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

    @Column(name = "coin_name", nullable = false, length = 100)
    private String coinName;

    @Column(name = "current_price", nullable = false, length = 400)
    private Long currentPrice;

    protected Coin() {
    }

    @Builder
    public Coin(String coinName, Long currentPrice) {
        this.coinName = coinName;
        this.currentPrice = currentPrice;
    }
}
