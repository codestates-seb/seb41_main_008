package com.nfteam.server.coin.repository;

import com.nfteam.server.coin.entity.CoinOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinOrderRepository extends JpaRepository<CoinOrder, Long> {
}