package com.nfteam.server.domain.cart.entity;

import com.nfteam.server.domain.item.entity.Item;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@Table(name = "cart_item_rel")
public class CartItemRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rel_id")
    private Long relId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected CartItemRel() {
    }

    @Builder
    public CartItemRel(Cart cart, Item item) {
        this.cart = cart;
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemRel that = (CartItemRel) o;
        return relId.equals(that.relId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relId);
    }

}