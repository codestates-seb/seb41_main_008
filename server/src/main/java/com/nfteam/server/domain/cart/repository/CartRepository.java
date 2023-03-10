package com.nfteam.server.domain.cart.repository;

import com.nfteam.server.domain.cart.entity.Cart;
import com.nfteam.server.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findCartByMemberAndPaymentYn(Member member, boolean paymentYn);

}