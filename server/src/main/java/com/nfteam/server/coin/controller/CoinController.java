package com.nfteam.server.coin.controller;

import com.nfteam.server.coin.Coin;
import com.nfteam.server.coin.service.CoinService;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/coins")
@Slf4j
public class CoinController {
    @Autowired
    private CoinService coinService;

    private final List<String> COIN = List.of("SOL","BTC","DOGE","ETH","ETC");


    @Scheduled(cron = "0 0 4 25 * *")
    @GetMapping("/withdrawl-fee")
    public void getWithdrwalFee() throws Exception {
        coinService.calculateFee(COIN);
        log.info("withdrawlfee updated successfully!");
    }

    @GetMapping("/coin/{coin}")
    public ResponseEntity getCoinDataByName(@PathVariable String CoinName){
        return new ResponseEntity<>(coinService.getCurrentPrice(CoinName), HttpStatus.OK);
    }





    //deleteMapping
    @DeleteMapping("/coin/{coinid}")
    public void deleteCoin(@PathVariable Long coinid){
        coinService.deleteCoin(coinid);
    }

    @DeleteMapping("/coin")
    public void deleteAll(){
        coinService.deleteAllCoin();
    }

}
