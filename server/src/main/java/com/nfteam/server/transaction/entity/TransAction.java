package com.nfteam.server.transaction.entity;

import com.nfteam.server.coin.Coin;
import com.nfteam.server.common.audit.BaseEntity;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "transaction")
public class TransAction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_id")
    private Long transId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id", nullable = false)
    private Coin coin;

    @Column(name = "trans_price", nullable = false)
    private Double transPrice;

    protected TransAction() {
    }

    @Builder
    public TransAction(Member seller, Member buyer, Item item, Coin coin, Double transPrice) {
        this.seller = seller;
        this.buyer = buyer;
        this.item = item;
        this.coin = coin;
        this.transPrice = transPrice;
    }

}
