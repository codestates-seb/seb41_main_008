package com.nfteam.server.item.entity;

import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.dto.response.item.ItemResponseDto;
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

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_img_name", nullable = false)
    private String itemImageName;

    // 상품의 현재 판매가능 여부
    @Column(name = "on_sale")
    private Boolean onSale;

    // 상품 가격 코인 갯수
    @Column(name = "coin_count")
    private Double coinCount;

    // 상품 고유 정보
    @OneToOne(mappedBy = "item", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private ItemCredential itemCredential;

    protected Item() {
    }

    @Builder
    public Item(Long itemId,
                String itemName,
                String itemImageName,
                Boolean onSale,
                Double coinCount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageName = itemImageName;
        this.onSale = onSale;
        this.coinCount = coinCount;
    }

    public void assignItemCredential(ItemCredential itemCredential) {
        this.itemCredential = itemCredential;
        itemCredential.assignItem(this);
    }

    public void assignCollection(ItemCollection collection) {
        this.collection = collection;
        collection.getItemList().add(this);
    }

    public void assignMember(Member member) {
        this.member = member;
        member.getItemList().add(this);
    }

    public ItemResponseDto toResponseDto() {
        return ItemResponseDto.builder()
                .itemId(itemId)
                .ownerId(member.getMemberId())
                .ownerName(member.getNickname())
                .itemName(itemName)
                .itemImageName(itemImageName)
                .onSale(onSale)
                .coinCount(coinCount)
                .build();
    }
}
