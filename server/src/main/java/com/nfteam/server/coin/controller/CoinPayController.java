package com.nfteam.server.coin.controller;

import com.nfteam.server.coin.service.CoinPayService;
import com.nfteam.server.dto.request.coin.CoinPurchaseRequest;
import com.nfteam.server.dto.response.common.SingleIdResponse;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coin-pay")
public class CoinPayController {

    private final CoinPayService coinPayService;

    @PostMapping
    public ResponseEntity init(@RequestBody @Valid CoinPurchaseRequest request,
                               @AuthenticationPrincipal MemberDetails memberDetails) {

        coinPayService.startPayment(request, memberDetails);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}