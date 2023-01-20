package com.nfteam.server.item.entity;

import com.nfteam.server.common.audit.BaseEntity;
import com.nfteam.server.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "item")
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    // 소속 컬렉션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id")
    private ItemCollection collection;

    // 아이템 현재 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_member_id")
    private Member member;

    // 상품 고유 정보
    @OneToOne(mappedBy = "item", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private ItemCredential itemCredential;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_img_name", nullable = false)
    private String itemImageName;

    @Column(name = "item_desc")
    private String itemDescription;

    // 상품의 현재 판매가능 여부
    @Column(name = "on_sale")
    private Boolean onSale;

    // 상품 가격 == 코인 갯수
    @Column(name = "item_price")
    private Double itemPrice;

    protected Item() {
    }

    @Builder
    public Item(String itemName,
                String itemImageName,
                String itemDescription,
                Boolean onSale,
                Double itemPrice) {
        this.itemName = itemName;
        this.itemImageName = itemImageName;
        this.itemDescription = itemDescription;
        this.onSale = onSale;
        this.itemPrice = itemPrice;
    }

    public void assignMember(Member member) {
        this.member = member;
        member.getItemList().add(this);
    }

    public void assignCollection(ItemCollection collection) {
        this.collection = collection;
        collection.getItemList().add(this);
    }

    public void assignItemCredential(ItemCredential itemCredential) {
        this.itemCredential = itemCredential;
        itemCredential.assignItem(this);
    }

    public void updatePrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void updateSaleStatus(Boolean onSale) {
        this.onSale = onSale;
    }

}