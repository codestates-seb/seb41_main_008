package com.nfteam.server.cart.repository;

import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findCartByMemberAndPaymentYn(Member member, boolean paymentYn);
    Optional<Cart> findCartByMember(Long memberId);
}
