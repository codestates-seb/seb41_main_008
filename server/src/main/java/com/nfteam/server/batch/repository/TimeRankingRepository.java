package com.nfteam.server.batch.repository;

import com.nfteam.server.batch.entity.TimeRankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRankingRepository extends JpaRepository<TimeRankingEntity, Long> {
}