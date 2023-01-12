package com.nfteam.server.coin.service;

import com.nfteam.server.coin.Coin;
import com.nfteam.server.coin.repository.CoinRepository;
import com.nfteam.server.openfeign.feign.UpbitFeeFeignClient;
import com.nfteam.server.openfeign.feign.UpbitFeignClient;
import com.nfteam.server.openfeign.model.UpbitEachWithdrawalFee;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinService {
    @Autowired
    UpbitFeignClient upbitFeignClient;
    @Autowired
    UpbitFeeFeignClient upbitFeeFeignClient;
    @Autowired
    CoinRepository coinRepository;


    public void calculateFee(List<String> coin) throws Exception{
        Map<String,Double> result =  upbitFeeFeignClient.getWithdrawalFee().getData()
            .stream()
            .collect(Collectors.toMap(
                UpbitEachWithdrawalFee::getCurrency,
                UpbitEachWithdrawalFee::getWithdrawFee
            ));
        for(String coinName : coin){
            Coin coinInfo = Coin.builder()
                .coinName(coinName)
                .withdrawlFee(result.get(coinName))
                .build();

            coinRepository.save(coinInfo);
        }

    }



    public Coin findCoin(String coin) {
        Optional<Coin> optionalCoin = coinRepository.findByCoinName(coin);
        return optionalCoin.get();
    }
}
