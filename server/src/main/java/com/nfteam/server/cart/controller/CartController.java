package com.nfteam.server.cart.controller;

import com.nfteam.server.cart.service.CartService;
import com.nfteam.server.dto.request.cart.CartPurchaseRequest;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    // 로그아웃 & purchase complete 시 호출 (프론트 로컬 스토리지 기록이 지워질 때)
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody CartPurchaseRequest cartPurchaseRequest,
                                     @AuthenticationPrincipal MemberDetails memberDetails) {
        cartService.saveCartRel(cartPurchaseRequest, memberDetails);
        return new ResponseEntity(HttpStatus.OK);
    }

}