package com.nfteam.server.item.entity;

import com.nfteam.server.audit.BaseEntity;
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

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_price", nullable = false)
    private Long itemPrice;

    @Column(name = "item_img_url", nullable = false)
    private String itemImageUrl;

    // 상품의 현재 판매가능 여부
    @Column(name = "on_sale", nullable = false)
    private boolean onSale;

    // 상품 고유 정보
    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private ItemCredential itemCredential;

    // 상품의 소속 그룹
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id")
    private ItemCollection collection;

    // 아이템 현재 소유자 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_member_id")
    private Member member;

    protected Item() {
    }

    @Builder
    public Item(Long itemId,
                String itemName,
                Long itemPrice,
                String itemImageUrl,
                boolean onSale) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImageUrl = itemImageUrl;
        this.onSale = onSale;
    }

    public void assignItemCredential(ItemCredential itemCredential) {
        this.itemCredential = itemCredential;
    }

    public void assignCollectionAndMember(ItemCollection collection, Member member){
        this.collection = collection;
        collection.getItemList().add(this);
        collection.assignMember(member);
    }


    public void addImgUrl(String url) {
        this.itemImageUrl = url;
    }

}
