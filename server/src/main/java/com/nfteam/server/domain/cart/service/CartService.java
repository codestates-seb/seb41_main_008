package com.nfteam.server.domain.cart.service;

import com.nfteam.server.domain.cart.entity.Cart;
import com.nfteam.server.domain.cart.entity.CartItemRel;
import com.nfteam.server.domain.cart.repository.CartItemRelRepository;
import com.nfteam.server.domain.cart.repository.CartRepository;
import com.nfteam.server.domain.item.entity.Item;
import com.nfteam.server.domain.item.repository.ItemRepository;
import com.nfteam.server.domain.member.entity.Member;
import com.nfteam.server.domain.member.repository.MemberRepository;
import com.nfteam.server.dto.request.cart.CartPurchaseRequest;
import com.nfteam.server.dto.response.cart.CartResponse;
import com.nfteam.server.exception.cart.CartExistException;
import com.nfteam.server.exception.cart.CartItemNotSaleException;
import com.nfteam.server.exception.cart.CartNotFoundException;
import com.nfteam.server.exception.item.ItemNotFoundException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.exception.transaction.TransRecordNotValidException;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@Service
public class CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRelRepository cartItemRelRepository;
    private final ItemRepository itemRepository;

    public CartService(MemberRepository memberRepository,
                       CartRepository cartRepository,
                       CartItemRelRepository cartItemRelRepository,
                       ItemRepository itemRepository) {
        this.memberRepository = memberRepository;
        this.cartRepository = cartRepository;
        this.cartItemRelRepository = cartItemRelRepository;
        this.itemRepository = itemRepository;
    }

    // 현재 활성화 된(미결제) 본인의 카트 조회
    @Transactional
    public CartResponse loadOwnCart(String email) {
        Member member = findMember(email);

        // 카트 기록 이상 검사 (결제되지 않은 카트 중복 여부)
        List<Cart> findCartList = cartRepository.findCartByMemberAndPaymentYn(member, false);
        validateCartSize(findCartList.size());

        // 카트 정보 가져오기
        Cart cart = getCart(member, findCartList);

        // 카트 내 아이템 리스트 조회
        List<CartItemRel> cartItemRelList = cartItemRelRepository.findByCartId(cart.getCartId());
        List<Long> itemIdList = cartItemRelList.stream()
                .map(rel -> rel.getItem().getItemId())
                .collect(Collectors.toList());

        return new CartResponse(cart.getCartId(), itemRepository.findItemResponseList(itemIdList));
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    // 미결제 카트의 갯수 체크 - 미결제 카트가 1개가 아니면 데이터 정합성의 문제가 있는 것
    private void validateCartSize(int size) {
        if (size > 1) throw new CartExistException();
    }

    private Cart getCart(Member member, List<Cart> findCartList) {
        // 현재 미결제 카트 정보가 없는 경우 신규 카트 정보 생성
        if (findCartList.size() == 0) {
            Cart cart = new Cart(member);
            return cartRepository.save(cart);
        } else {
            return findCartList.get(0);
        }
    }

    // 장바구니 기록 저장
    @Transactional
    public void saveCartRel(CartPurchaseRequest cartPurchaseRequest, MemberDetails memberDetails) {
        Member member = findMember(memberDetails.getEmail());
        Cart cart = findCart(cartPurchaseRequest.getCartId());

        // 본인 카트 검증
        validateOwnCart(member, cart);

        // 기존 장바구니 아이템 연관관계 기록은 삭제
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

    // DB에 기록된 카트 소유자와 현재 회원 일치 여부 확인
    private void validateOwnCart(Member member, Cart cart) {
        // 카트가 결제 상태 이거나(카트 이상), 카트 소유자와 현재 회원이 불일치 할 경우(회원 이상)
        if (cart.getPaymentYn() || (cart.getMember().getMemberId() != member.getMemberId())) {
            log.warn("장바구니 정보가 올바르지 않습니다. cartId : {}, member : {}", cart.getCartId(), member.getEmail());
            throw new TransRecordNotValidException("장바구니 정보가 올바르지 않습니다. - 관리자 체크 요망");
        }
    }

    private Item findItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    // 아이템 리스트 중 판매가 된 아이템이 섞여있는지 검사
    private void checkItemSaleStatus(List<Item> items) {
        List<Long> notSaleList = new ArrayList<>();

        items.stream().forEach(i -> {
            if (!i.getOnSale()) notSaleList.add(i.getItemId());
        });

        // 판매 불가능한 상품이 섞여있을 경우 해당 아이디 리스트를 에러메시지로 반환
        if (!notSaleList.isEmpty()) {
            throw new CartItemNotSaleException(notSaleList);
        }
    }

    private Cart findCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException());
    }

}