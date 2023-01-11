package com.nfteam.server.coin.service;

import com.nfteam.server.coin.Coin;
import com.nfteam.server.coin.repository.CoinRepository;
import com.nfteam.server.openfeign.feign.UpbitFeignClient;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinService {
    @Autowired
    UpbitFeignClient upbitFeignClient;
    @Autowired
    CoinRepository coinRepository;

    public void getPrice(List<String> coin) {

        for(String coinName : coin){
            Coin findCoin = findCoin(coinName);
            double tradePrice = upbitFeignClient.getCoinPrice("KRW-" + coinName)
                .get(0).getTrade_price();

            findCoin.changeTradePrice(tradePrice);
            coinRepository.save(findCoin);

        }

    }


    public Coin findCoin(String coin) {
        Optional<Coin> optionalCoin = coinRepository.findByCoinName(coin);
        return optionalCoin.get();
    }
}
