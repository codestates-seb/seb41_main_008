package com.nfteam.server.cart.service;

import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.cart.entity.CartItemRel;
import com.nfteam.server.cart.repository.CartItemRelRepository;
import com.nfteam.server.cart.repository.CartRepository;
import com.nfteam.server.dto.request.cart.CartPurchaseRequest;
import com.nfteam.server.dto.response.cart.CartResponse;
import com.nfteam.server.exception.cart.CartExistException;
import com.nfteam.server.exception.cart.CartItemNotSaleException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRelRepository cartItemRelRepository;
    private final ItemRepository itemRepository;
    private final QItemRepository qitemRepository;

    public CartService(MemberRepository memberRepository,
                       CartRepository cartRepository,
                       CartItemRelRepository cartItemRelRepository,
                       ItemRepository itemRepository,
                       QItemRepository qitemRepository) {
        this.memberRepository = memberRepository;
        this.cartRepository = cartRepository;
        this.cartItemRelRepository = cartItemRelRepository;
        this.itemRepository = itemRepository;
        this.qitemRepository = qitemRepository;
    }

    @Transactional
    public CartResponse loadOwnCart(String email) {
        Member member = findMember(email);

        // 카트 기록 이상 검사 (결제되지 않은 카트 중복 여부)
        List<Cart> findCartList = cartRepository.findCartByMemberAndPaymentYn(member, false);
        validateCartSize(findCartList.size());

        // 신규 카트 or 기존 카트 가져오기
        Cart cart = getCart(member, findCartList);

        // 장바구니 아이템 리스트 조회
        List<CartItemRel> cartItemRelList = cartItemRelRepository.findByCartId(cart.getCartId());
        List<Long> itemIdList = cartItemRelList.stream()
                .map(rel -> rel.getItem().getItemId())
                .collect(Collectors.toList());

        return new CartResponse(cart.getCartId(), qitemRepository.findItemList(itemIdList));
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    private void validateCartSize(int size) {
        if (size > 1) throw new CartExistException();
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
     * Cart: paymentYn - 결제완료 처리는 TransAction 완료 후 성립 & 결제 완료 후 해당 멤버에게 새로운 카트 배정
     * Item: onSale - 판매완료 처리는 TransAction 완료 후 성립
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

    private void validateOwnCart(Member member, Cart cart) {
        if (cart.getPaymentYn() || (cart.getMember().getMemberId() != member.getMemberId())) {
            throw new TransRecordNotValidException("본인의 장바구니 정보가 아닙니다. (기록 이상)");
        }
    }

    private Item findItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    private void checkItemSaleStatus(List<Item> items) {
        List<Long> notSaleList = new ArrayList<>();

        items.stream().forEach(i -> {
            if (!i.getOnSale()) notSaleList.add(i.getItemId());
        });

        // 판매 불가능한 상품이 섞여있을 경우 해당 아이디 리스트를 에러메시지로 반환
        if (!notSaleList.isEmpty()) {
            StringBuilder message = new StringBuilder();
            notSaleList.stream().forEach(id -> {
                message.append(id).append(",");
            });
            message.deleteCharAt(message.lastIndexOf(","));
            throw new CartItemNotSaleException(message.toString());
        }
    }

    private Cart findCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException());
    }

}