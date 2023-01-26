package com.nfteam.server.coin.controller;

import com.nfteam.server.coin.service.CoinService;
import com.nfteam.server.dto.request.coin.CoinPurchaseRequest;
import com.nfteam.server.dto.response.coin.CoinPurchaseReadyResponse;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coins")
public class CoinController {

    private final CoinService coinService;

    @GetMapping("/my")
    public ResponseEntity getMemberCoinList(@AuthenticationPrincipal MemberDetails memberDetails) {
        return new ResponseEntity<>(coinService.getMemberCoinList(memberDetails.getMemberId()), HttpStatus.OK);
    }

    @GetMapping("/fee/{coinId}")
    public ResponseEntity<Double> getCoinFee(@PathVariable("coinId") Long coinId) {
        return new ResponseEntity<>(coinService.getCoinFee(coinId), HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ResponseEntity<CoinPurchaseReadyResponse> purchase(@RequestBody @Valid CoinPurchaseRequest request,
                                                              @AuthenticationPrincipal MemberDetails memberDetails,
                                                              Model model) {
        CoinPurchaseReadyResponse response = coinService.startPayment(request, memberDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/approve")
    public ResponseEntity approve(@RequestParam("pg_token") String pgToken) throws IOException {
        return new ResponseEntity<>(coinService.approvePayment(pgToken, ""), HttpStatus.OK);
    }

    @GetMapping("/cancel")
    public ResponseEntity cancel() {
        return new ResponseEntity(HttpStatus.REQUEST_TIMEOUT);
    }

    @GetMapping("/fail")
    public ResponseEntity fail() {
        return new ResponseEntity(HttpStatus.REQUEST_TIMEOUT);
    }

}