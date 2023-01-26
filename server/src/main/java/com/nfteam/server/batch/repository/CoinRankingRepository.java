package com.nfteam.server.batch.repository;

import com.nfteam.server.batch.entity.CoinRankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRankingRepository extends JpaRepository<CoinRankingEntity, Long> {
}