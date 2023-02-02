package com.nfteam.server.domain.cart.entity;

import com.nfteam.server.domain.audit.BaseEntity;
import com.nfteam.server.domain.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name = "cart")
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // true 이전에 거래 완료된 카트 (결제 완료)
    // false 현재 활성화 카트 (아직 결제 전)
    @Column(name = "payment_yn", nullable = false)
    private Boolean paymentYn;

    // 장바구니 아이템 리스트
    @OneToMany(mappedBy = "cart")
    private List<CartItemRel> itemList = new ArrayList<>();

    protected Cart() {
    }

    // 신규 카트 배정
    public Cart(Member member) {
        this.member = member;
        member.getCartList().add(this);
        this.paymentYn = false;
    }

    public void changePaymentYn(Boolean paymentYn) {
        this.paymentYn = paymentYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return cartId.equals(cart.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId);
    }
}