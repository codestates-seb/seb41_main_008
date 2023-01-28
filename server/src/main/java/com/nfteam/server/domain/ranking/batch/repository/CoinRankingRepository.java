package com.nfteam.server.domain.ranking.batch.repository;

import com.nfteam.server.domain.ranking.batch.entity.CoinRankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRankingRepository extends JpaRepository<CoinRankingEntity, Long> {
}