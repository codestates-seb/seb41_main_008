package com.nfteam.server.item.entity;

import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.cart.entity.CartItemRel;
import com.nfteam.server.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "item")
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    // 상품의 현재 판매가능 여부
    @Column(name = "sell_status", nullable = false)
    private Boolean sellStatus = true;

    // 상품의 소속 그룹
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private ItemGroup group;

    // 아이템 현재 소유자 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_member_id")
    private Member owner;

    // 현재 이 아이템이 속해있는 장바구니들의 목록
    // 결제가 완료되어 판매 가능 여부 상태값이 변하면
    // 모든 장바구니에서 해당 아이템을 삭제하는 로직이 필요
    @OneToMany(mappedBy = "item")
    private List<CartItemRel> relatedCartList = new ArrayList<>();

    protected Item() {
    }

}
