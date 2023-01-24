package com.nfteam.server.coin.controller;

import com.nfteam.server.coin.service.CoinFeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coin-fee")
@Slf4j
public class CoinFeeController {

    private static final List<String> COIN = List.of("SOL", "BTC", "DOGE", "ETH", "ETC");
    private final CoinFeeService coinFeeService;

    // 매 주 일요일 오전 7시 수수료 기록 갱신
    @Scheduled(cron = "0 0 7 ? * SUN")
    @GetMapping
    public void getWithdrawFee() throws Exception {
        // 수수료 변화 기록 insert + 신규 코인 수수료 갱신
        coinFeeService.saveCoinFeeHistoryAndUpdateFee(COIN);
        log.info("withdraw-fee updated successfully!");
    }

}