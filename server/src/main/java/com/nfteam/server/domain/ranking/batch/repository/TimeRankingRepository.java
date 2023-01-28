package com.nfteam.server.domain.ranking.batch.repository;

import com.nfteam.server.domain.ranking.batch.entity.TimeRankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRankingRepository extends JpaRepository<TimeRankingEntity, Long> {
}