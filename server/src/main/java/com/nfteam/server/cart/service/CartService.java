package com.nfteam.server.cart.service;

import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.cart.repository.CartRepository;
import com.nfteam.server.exception.cart.CartNotFoundException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import com.nfteam.server.member.service.MemberService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CartService {

    private final MemberService memberService;
    private final CartRepository cartRepository;

    public Cart createCart(long memberId) {
        Cart cart = new Cart();
        Member findMember = memberService.findMember(memberId);
        cart.assignOwner(findMember);
        return cartRepository.save(cart);
    }

    public Cart updateCart(long memberId) {
        Cart findCart = this.findVerifiedCart(memberId);
        findCart.changePaymentYn(!findCart.getPaymentYn());
        return findCart;
    }

    public Cart findVerifiedCart(long memberId) {
        Cart findCart = cartRepository.findCartByMemberAndPaymentYn(memberId, false)
            .orElseThrow(() ->
                new CartNotFoundException());

        return findCart;
    }

}
