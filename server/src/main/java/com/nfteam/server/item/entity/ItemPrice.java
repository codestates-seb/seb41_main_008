package com.nfteam.server.item.entity;

import com.nfteam.server.coin.Coin;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "item_price")
public class ItemPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    private Long priceId;

    // 코인의 갯수
    @Column(name = "coin_count", nullable = false)
    private Double coinCount;

    // 상품의 종류
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 코인의 종류
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private Coin coin;

    protected ItemPrice() {
    }
}
