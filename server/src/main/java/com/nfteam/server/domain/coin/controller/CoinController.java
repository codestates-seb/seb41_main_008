package com.nfteam.server.domain.coin.controller;

import com.nfteam.server.domain.coin.service.CoinService;
import com.nfteam.server.dto.request.coin.CoinPurchaseRequest;
import com.nfteam.server.dto.response.coin.CoinPurchaseReadyResponse;
import com.nfteam.server.security.userdetails.MemberDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/coins")
public class CoinController {

    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    // 현재 회원의 코인 지갑 정보 조회
    @GetMapping("/my")
    public ResponseEntity getMemberCoinList(@AuthenticationPrincipal MemberDetails memberDetails) {
        return new ResponseEntity<>(coinService.getMemberCoinList(memberDetails.getMemberId()), HttpStatus.OK);
    }

    // 해당 코인의 수수료 정보 조회
    @GetMapping("/fee/{coinId}")
    public ResponseEntity<Double> getCoinFee(@PathVariable("coinId") Long coinId) {
        return new ResponseEntity<>(coinService.getCoinFee(coinId), HttpStatus.OK);
    }

    // 코인 구매 요청
    @PostMapping("/purchase")
    public ResponseEntity<CoinPurchaseReadyResponse> purchase(@RequestBody @Valid CoinPurchaseRequest request,
                                                              @AuthenticationPrincipal MemberDetails memberDetails) {
        return new ResponseEntity<>(coinService.startPayment(request, memberDetails), HttpStatus.OK);
    }

    // 코인 결제 승인 요청
    @GetMapping(value = "/approve")
    public ResponseEntity approve(@RequestParam(value = "pg_token") String pgToken,
                                  @RequestParam(value = "tid") String tid) {
        return new ResponseEntity<>(coinService.approvePayment(pgToken, tid), HttpStatus.MOVED_PERMANENTLY);
    }

    // 코인 결제 완료 요청
    @GetMapping(value = "/success")
    public ResponseEntity success(@RequestParam(value = "tid") String tid) {
        return new ResponseEntity<>(coinService.getCoinOrderInfo(tid), HttpStatus.OK);
    }

}