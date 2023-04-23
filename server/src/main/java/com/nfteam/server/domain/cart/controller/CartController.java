package com.nfteam.server.domain.cart.controller;

import com.nfteam.server.domain.cart.service.CartService;
import com.nfteam.server.dto.request.cart.CartPurchaseRequest;
import com.nfteam.server.security.userdetails.MemberDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 장바구니 기록 저장
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody CartPurchaseRequest cartPurchaseRequest,
                                     @AuthenticationPrincipal MemberDetails memberDetails) {
        cartService.saveCartRel(cartPurchaseRequest, memberDetails);
        return new ResponseEntity(HttpStatus.OK);
    }

}