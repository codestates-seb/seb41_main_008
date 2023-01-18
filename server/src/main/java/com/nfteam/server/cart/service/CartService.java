package com.nfteam.server.cart.service;

import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.cart.entity.CartItemRel;
import com.nfteam.server.cart.repository.CartItemRelRepository;
import com.nfteam.server.cart.repository.CartRepository;
import com.nfteam.server.exception.cart.CartExistException;
import com.nfteam.server.exception.cart.CartNotFoundException;
import com.nfteam.server.exception.item.ItemNotFoundException;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.repository.ItemRepository;
import com.nfteam.server.member.entity.Member;
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

    private final CartItemRelRepository cartItemRelRepository;

    private final ItemRepository itemRepository;

    public Cart createCart(Long memberId) {
        this.verifyCart(memberId);

        Cart cart = new Cart();
        Member findMember = memberService.findMember(memberId);
        cart.assignOwner(findMember);
        return cartRepository.save(cart);
    }

    public Cart updateCart(Long memberId) {
        Cart findCart = this.findVerifiedCart(memberId);
        findCart.changePaymentYn(!findCart.getPaymentYn());
        return findCart;
    }

    public Cart findVerifiedCart(Long memberId) {
        Cart findCart = cartRepository.findCartByMemberAndPaymentYn(new Member(memberId), false)
            .orElseThrow(() ->
                new CartNotFoundException());

        return findCart;
    }

    public void verifyCart(Long memberId) {
        Optional<Cart> cartByMember = cartRepository.findCartByMemberAndPaymentYn(
            new Member(memberId), false);
        if (cartByMember.isPresent()) {
            throw new CartExistException();
        }
    }

    public CartItemRel insertCartItem(Long cartId, Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(itemId));
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException());

        CartItemRel cartItemRel = CartItemRel.builder().item(item).cart(cart).build();

        return cartItemRelRepository.save(cartItemRel);

    }

}
