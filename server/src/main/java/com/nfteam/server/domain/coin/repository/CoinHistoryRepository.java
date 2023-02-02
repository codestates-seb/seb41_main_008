package com.nfteam.server.domain.coin.repository;

import com.nfteam.server.domain.coin.entity.CoinFeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinHistoryRepository extends JpaRepository<CoinFeeHistory, Long> {
}
