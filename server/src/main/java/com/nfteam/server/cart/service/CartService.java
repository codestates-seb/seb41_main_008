package com.nfteam.server.cart.service;

import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.cart.entity.CartItemRel;
import com.nfteam.server.cart.repository.CartItemRelRepository;
import com.nfteam.server.cart.repository.CartRepository;
import com.nfteam.server.dto.request.cart.CartPurchaseRequest;
import com.nfteam.server.dto.response.cart.CartResponse;
import com.nfteam.server.exception.cart.CartNotFoundException;
import com.nfteam.server.exception.item.ItemNotFoundException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.exception.transaction.TransRecordNotValidException;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.repository.ItemRepository;
import com.nfteam.server.item.repository.QItemRepository;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRelRepository cartItemRelRepository;
    private final QItemRepository qitemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public CartResponse loadOwnCart(String email) {
        Member member = findMember(email);
        List<Cart> findCartList = cartRepository.findCartByMemberAndPaymentYn(member, false);

        // 카트 기록 이상 검사 (결제되지 않은 카트 중복 여부)
        validateCartSize(findCartList.size());
        Cart cart = getCart(member, findCartList);

        List<CartItemRel> cartItemRels
                = cartItemRelRepository.findByCartId(cart.getCartId());

        List<Long> itemIdList = cartItemRels.stream()
                .map(rel -> rel.getItem().getItemId())
                .collect(Collectors.toList());

        return new CartResponse(cart.getCartId(),
                qitemRepository.findItemList(itemIdList));
    }

    private void validateCartSize(int size) {
        if (size > 1) throw new TransRecordNotValidException("결제되지 않은 카트 기록이 다수 존재 (기록 이상)");
    }

    private Cart getCart(Member member, List<Cart> findCartList) {
        if (findCartList.size() == 0) {
            Cart cart = new Cart(member);
            return cartRepository.save(cart);
        } else {
            return findCartList.get(0);
        }
    }

    /**
     * Cart: paymentYn - 결제완료 처리는 TransAction 처리완료 후 성립 => 해당 멤버에게 새로운 카트 배정 필요
     * Item: onSale - 판매완료 처리는 TransAction 처리완료 후 성립
     */
    @Transactional
    public void saveCartRel(CartPurchaseRequest cartPurchaseRequest, MemberDetails memberDetails) {
        Member member = findMember(memberDetails.getEmail());
        Cart cart = findCart(cartPurchaseRequest.getCartId());

        // 본인 카트 검증
        validateOwnCart(member, cart);

        // 기존 장바구니 연관관계 기록은 삭제
        cartItemRelRepository.deleteByCartId(cart.getCartId());

        // 장바구니에 아이템이 존재하면 연관관계 생성 후 저장
        if (!cartPurchaseRequest.getItemIdList().isEmpty()) {
            // 아이템 존재 여부 검증 및 아이템 리스트 로드
            List<Item> items = cartPurchaseRequest
                    .getItemIdList()
                    .stream()
                    .map(id -> findItem(id))
                    .collect(Collectors.toList());

            // 판매 중 상품 검증
            checkItemSaleStatus(items);

            List<CartItemRel> cartItemRelList = items
                    .stream()
                    .map(item -> new CartItemRel(cart, item))
                    .collect(Collectors.toList());

            // 새로운 장바구니 기록 추가
            cartItemRelRepository.saveAll(cartItemRelList);
        }
    }

    private Item findItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    private void checkItemSaleStatus(List<Item> items) {
        items.stream().forEach(i -> {
            if (!i.getOnSale()) throw new TransRecordNotValidException("판매되지 않은 상품이 섞여 있습니다.");
        });
    }

    private void validateOwnCart(Member member, Cart cart) {
        if (cart.getPaymentYn() || (cart.getMember().getMemberId() != member.getMemberId())) {
            throw new TransRecordNotValidException("본인의 장바구니 정보가 아닙니다. (기록 이상)");
        }
    }

    private Cart findCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException());
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

}