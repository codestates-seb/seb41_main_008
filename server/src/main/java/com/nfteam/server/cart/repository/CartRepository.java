package com.nfteam.server.cart.repository;

import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findCartByMemberAndPaymentYn(Member member, boolean paymentYn);

}