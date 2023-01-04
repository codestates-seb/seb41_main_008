package com.nfteam.server.cart.entity;

import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "cart")
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    // 카트의 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 카트의 상태
    // true 현재 회원의 활성화 된 카트(아직 결제 전)
    // false 이전에 거래가 완료된 카트 기록(결제 완료)
    // 회원 카트 조회 시 memberActive = true 인 카드를 보여주도록 한다.
    @Column(name = "cart_active", nullable = false)
    private Boolean cartActive = true;

    // 장바구니에 담긴 아이템 리스트
    @OneToMany(mappedBy = "cart")
    private List<CartItemRel> itemList = new ArrayList<>();

    protected Cart() {
    }

    public void assignMember(Member member) {
        this.member = member;
        member.getCartList().add(this);
    }

}
