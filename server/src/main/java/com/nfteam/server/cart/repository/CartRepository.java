package com.nfteam.server.cart.repository;

import com.nfteam.server.cart.entity.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findCartByMemberAndPaymentYn(Long memberId, boolean paymentYn);
}
