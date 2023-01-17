package com.nfteam.server.coin.service;

import com.nfteam.server.coin.entity.Coin;
import com.nfteam.server.coin.entity.CoinFeeHistory;
import com.nfteam.server.coin.repository.CoinHistoryRepository;
import com.nfteam.server.coin.repository.CoinRepository;
import com.nfteam.server.openfeign.feign.UpbitFeeFeignClient;
import com.nfteam.server.openfeign.feign.UpbitFeignClient;
import com.nfteam.server.openfeign.model.UpbitEachWithdrawalFee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoinService {

    private final UpbitFeignClient upbitFeignClient;
    private final UpbitFeeFeignClient upbitFeeFeignClient;
    private final CoinRepository coinRepository;
    private final CoinHistoryRepository coinHistoryRepository;

    public void saveFeeHistory(List<String> coin) throws Exception {
        Map<String, Double> result = this.crawling();

        for (String coinName : coin) {
            CoinFeeHistory coinInfo = CoinFeeHistory.builder().coinName(coinName).withdrawlFee(result.get(coinName)).build();
            coinHistoryRepository.save(coinInfo);
        }
    }

    public void updateFee(List<String> coin) throws Exception {
        Map<String, Double> result = this.crawling();

        for (String coinName : coin) {
            Coin coinInfo = findCoin(coinName);

            coinInfo.changeWithdrawlFee(result.get(coinName));
            coinRepository.save(coinInfo);
        }
    }

    Map<String, Double> crawling() throws IOException {
        return upbitFeeFeignClient
                .getWithdrawalFee()
                .getData().stream()
                .collect(Collectors.toMap(
                        UpbitEachWithdrawalFee::getCurrency,
                        UpbitEachWithdrawalFee::getWithdrawFee)
                );
    }

    public Coin findCoin(String coinName) {
        Optional<Coin> coin = coinRepository.findByCoinName(coinName);
        if (coin.isPresent()) {
            return coin.get();
        }
        return coinRepository.save(Coin.builder().coinName(coinName).build());
    }

}
