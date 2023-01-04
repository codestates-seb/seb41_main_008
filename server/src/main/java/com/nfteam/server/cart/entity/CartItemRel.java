package com.nfteam.server.cart.entity;

import com.nfteam.server.item.entity.Item;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "cart_item_rel")
public class CartItemRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rel_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected CartItemRel() {
    }

}
