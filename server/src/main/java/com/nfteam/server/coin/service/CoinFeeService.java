package com.nfteam.server.coin.service;

import com.nfteam.server.coin.entity.Coin;
import com.nfteam.server.coin.entity.CoinFeeHistory;
import com.nfteam.server.coin.repository.CoinHistoryRepository;
import com.nfteam.server.coin.repository.CoinRepository;
import com.nfteam.server.coin.utils.UpbitEachWithdrawFee;
import com.nfteam.server.coin.utils.UpbitFeeFeignClient;
import com.nfteam.server.exception.coin.CoinNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoinFeeService {

    private final UpbitFeeFeignClient upbitFeeFeignClient;
    private final CoinRepository coinRepository;
    private final CoinHistoryRepository coinHistoryRepository;

    @Transactional
    public void saveCoinFeeHistoryAndUpdateFee(List<String> coinList) throws Exception {
        Map<String, Double> result = crawling();
        List<CoinFeeHistory> coinFeeHistories = new ArrayList<>();

        for (String coinName : coinList) {
            CoinFeeHistory coinFeeHistory = CoinFeeHistory.builder()
                    .coinName(coinName)
                    .withdrawFee(result.get(coinName))
                    .build();
            coinFeeHistories.add(coinFeeHistory);
        }

        // 히스토리 기록 저장
        coinHistoryRepository.saveAll(coinFeeHistories);

        // 코인 가격 업데이트
        updateCoinFee(result, coinList);
    }

    private Map<String, Double> crawling() throws IOException {
        return upbitFeeFeignClient
                .getWithdrawFee()
                .getData()
                .stream()
                .collect(Collectors.toMap(
                        UpbitEachWithdrawFee::getCurrency,
                        UpbitEachWithdrawFee::getWithdrawFee)
                );
    }

    public void updateCoinFee(Map<String, Double> result, List<String> coinList) throws Exception {
        for (String coinName : coinList) {
            Coin coin = findCoinByName(coinName);
            coin.changeWithdrawFee(result.get(coinName));
        }
    }

    private Coin findCoinByName(String coinName) {
        return coinRepository.findByCoinName(coinName)
                .orElseThrow(() -> new CoinNotFoundException(coinName));
    }

}