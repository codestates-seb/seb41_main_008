package com.nfteam.server.domain.transaction.entity;

import com.nfteam.server.domain.audit.BaseEntity;
import com.nfteam.server.domain.coin.entity.Coin;
import com.nfteam.server.domain.item.entity.Item;
import com.nfteam.server.domain.item.entity.ItemCollection;
import com.nfteam.server.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

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
    @JoinColumn(name = "collection_id", nullable = false)
    private ItemCollection collection;

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
    public TransAction(Member seller,
                       Member buyer,
                       ItemCollection collection,
                       Item item,
                       Coin coin,
                       Double transPrice) {
        this.seller = seller;
        this.buyer = buyer;
        this.collection = collection;
        this.item = item;
        this.coin = coin;
        this.transPrice = transPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransAction that = (TransAction) o;
        return transId.equals(that.transId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transId);
    }

}