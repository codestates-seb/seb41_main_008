package com.nfteam.server.coin.repository;

import com.nfteam.server.coin.entity.CoinFeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinHistoryRepository extends JpaRepository<CoinFeeHistory,Long> {

}
