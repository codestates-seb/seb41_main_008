package com.nfteam.server.domain.cart.repository;

import com.nfteam.server.domain.cart.entity.CartItemRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRelRepository extends JpaRepository<CartItemRel, Long> {

    @Query("select rel from CartItemRel rel where rel.cart.cartId =:cartId")
    List<CartItemRel> findByCartId(Long cartId);

    @Modifying
    @Query("delete from CartItemRel rel where rel.cart.cartId =:cartId")
    void deleteByCartId(Long cartId);

}