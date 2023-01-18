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
        //결제가 완료되면 다시 새로운 카트 생성
        this.createCart(memberId);
        return findCart;
    }

    public CartItemRel insertCartItem(Long memberId, Long itemId) {
        Cart cart;
        CartItemRel cartItemRel = CartItemRel.builder().build();
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(itemId));
        try {
            //item을 넣으려고 할때 카트가 생성되어있지 않다면 카트생성
            cart = this.findVerifiedCart(memberId);
            cartItemRel = CartItemRel.builder().item(item).cart(cart).build();
        } catch (CartNotFoundException e) {
            this.createCart(memberId);
        }

        return cartItemRelRepository.save(cartItemRel);

    }

    public Cart findVerifiedCart(Long memberId) {
        //멤버의 결제가 아직되지 않은 카트 가져온다
        Cart findCart = cartRepository.findCartByMemberAndPaymentYn(new Member(memberId), false)
            .orElseThrow(() ->
                new CartNotFoundException());

        return findCart;
    }

    public void verifyCart(Long memberId) {
        //존재하는지 확인
        Optional<Cart> cartByMember = cartRepository.findCartByMemberAndPaymentYn(
            new Member(memberId), false);
        if (cartByMember.isPresent()) {
            throw new CartExistException();
        }
    }


}
