package com.nfteam.server.coin.controller;

import com.nfteam.server.coin.service.CoinService;
import com.nfteam.server.dto.request.coin.CoinPurchaseRequest;
import com.nfteam.server.dto.response.coin.CoinPurchaseReadyResponse;
import com.nfteam.server.security.userdetails.MemberDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/coins")
public class CoinController {

    private static final String REDIRECT_URL = "http://localhost:3000/success";

    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

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
                                                              @AuthenticationPrincipal MemberDetails memberDetails) {
        return new ResponseEntity<>(coinService.startPayment(request, memberDetails), HttpStatus.OK);
    }

    @GetMapping(value = "/approve")
    public ResponseEntity approve(@RequestParam(value = "pg_token") String pgToken,
                                  @RequestParam(value = "tid") String tid) {
        coinService.approvePayment(pgToken, tid);
        return new ResponseEntity<>(getRedirectHttpHeaders(), HttpStatus.MOVED_PERMANENTLY);
    }

    private static HttpHeaders getRedirectHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(REDIRECT_URL));
        return headers;
    }

}