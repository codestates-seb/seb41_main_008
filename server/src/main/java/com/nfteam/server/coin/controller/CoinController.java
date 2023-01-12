package com.nfteam.server.coin.controller;

import com.nfteam.server.coin.service.CoinService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//실시간 websocket 또는 SSE?
@RestController
@RequestMapping("/coins")
public class CoinController {
    @Autowired
    private CoinService coinService;

    private final List<String> COIN = List.of("SOL","BTC","DOGE","ETH","ETC");


//    @GetMapping("/price")
//    public ResponseEntity getPrice(){
//        coinService.getPrice(COIN);
//        return new ResponseEntity("최근거래가 업데이트 완료.", HttpStatus.OK);
//    }


    @GetMapping("/withdrawl-fee")
    public ResponseEntity getWithdrwalFee() throws Exception {
        coinService.calculateFee(COIN);
        return new ResponseEntity("거래수수료 업데이트 완료.", HttpStatus.OK);
    }

}
