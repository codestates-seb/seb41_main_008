package com.nfteam.server.coin.entity;

import com.nfteam.server.common.audit.BaseEntity;
import com.nfteam.server.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "coin_order")
public class CoinOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private Coin coin;

    @Column(name = "coin_count")
    private Double coinCount;

    private Boolean payStatus;

    protected CoinOrder() {
    }

    @Builder
    public CoinOrder(Member buyer, Coin coin, Double coinCount) {
        this.buyer = buyer;
        this.coin = coin;
        this.coinCount = coinCount;
        this.payStatus = false;
    }

}