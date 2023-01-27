package com.nfteam.server.coin.entity;

import com.nfteam.server.common.audit.BaseEntity;
import com.nfteam.server.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "coin_order")
public class CoinOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    // 카카오 페이 결제 번호
    @Column(name = "pay_tid")
    private String tid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private Coin coin;

    @Column(name = "coin_count")
    private Double coinCount;

    @Column(name = "total_price")
    private Double totalPrice;

    private Boolean payStatus;

    protected CoinOrder() {
    }

    @Builder
    public CoinOrder(Member buyer,
                     Coin coin,
                     Double coinCount,
                     Double totalPrice) {
        this.buyer = buyer;
        this.coin = coin;
        this.coinCount = coinCount;
        this.totalPrice = totalPrice;
        this.payStatus = false;
    }

    public void updateTid(String tid) {
        this.tid = tid;
    }

    public void updatePayStatusTrue() {
        this.payStatus = true;
    }

}