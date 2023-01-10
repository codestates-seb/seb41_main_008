package com.nfteam.server.coin.service;

import com.nfteam.server.coin.feign.BithumbFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BithumbMarketService implements MarketService {

    @Autowired
    BithumbFeignClient bithumbFeignClient;
    @Override
    public double getCoinCurrentPrice(String coin) {
        return Double.parseDouble(
            bithumbFeignClient.getCoinPrice(coin.toUpperCase() + "_KRW").getData().getClosing_price());
    }
}
