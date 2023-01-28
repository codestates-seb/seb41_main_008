package com.nfteam.server.domain.coin.service;

import com.nfteam.server.domain.coin.entity.Coin;
import com.nfteam.server.domain.coin.entity.CoinFeeHistory;
import com.nfteam.server.domain.coin.repository.CoinHistoryRepository;
import com.nfteam.server.domain.coin.repository.CoinRepository;
import com.nfteam.server.domain.coin.utils.UpbitEachWithdrawFee;
import com.nfteam.server.domain.coin.utils.UpbitFeeFeignClient;
import com.nfteam.server.exception.coin.CoinNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CoinFeeService {

    private static final List<String> COIN = List.of("SOL", "BTC", "DOGE", "ETH", "ETC");

    private final UpbitFeeFeignClient upbitFeeFeignClient;
    private final CoinRepository coinRepository;
    private final CoinHistoryRepository coinHistoryRepository;

    public CoinFeeService(UpbitFeeFeignClient upbitFeeFeignClient,
                          CoinRepository coinRepository,
                          CoinHistoryRepository coinHistoryRepository) {
        this.upbitFeeFeignClient = upbitFeeFeignClient;
        this.coinRepository = coinRepository;
        this.coinHistoryRepository = coinHistoryRepository;
    }

    // 매 주 일요일 오전 7시 수수료 기록 갱신
    @Scheduled(cron = "0 0 7 ? * SUN")
    @Transactional
    public void saveCoinFeeHistoryAndUpdateFee() throws Exception {
        Map<String, Double> result = crawling();
        List<CoinFeeHistory> coinFeeHistories = new ArrayList<>();

        for (String coinName : COIN) {
            CoinFeeHistory coinFeeHistory = CoinFeeHistory.builder()
                    .coinName(coinName)
                    .withdrawFee(result.get(coinName))
                    .build();
            coinFeeHistories.add(coinFeeHistory);
        }

        // 히스토리 기록 저장
        coinHistoryRepository.saveAll(coinFeeHistories);

        // 코인 가격 업데이트
        updateCoinFee(result, COIN);

        log.info("withdraw-fee updated successfully!");
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

    public void updateCoinFee(Map<String, Double> result, List<String> coinList) {
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