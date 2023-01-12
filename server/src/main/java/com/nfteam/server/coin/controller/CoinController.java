package com.nfteam.server.coin.controller;

import com.nfteam.server.coin.service.CoinService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/coins")
@Slf4j
public class CoinController {
    @Autowired
    private CoinService coinService;

    private final List<String> COIN = List.of("SOL","BTC","DOGE","ETH","ETC");


//    @GetMapping("/price")
//    public ResponseEntity getPrice(){
//        coinService.getPrice(COIN);
//        return new ResponseEntity("최근거래가 업데이트 완료.", HttpStatus.OK);
//    }

    @Scheduled(cron = "0 0 10 25 * *")
    @GetMapping("/withdrawl-fee")
    public void getWithdrwalFee() throws Exception {
        coinService.calculateFee(COIN);
        log.info("withdrawlfee updated successfully!");
    }

}
