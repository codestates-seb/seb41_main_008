package com.nfteam.server.domain.ranking.repository;

import com.nfteam.server.domain.ranking.entity.DailyAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyAggregateRepository extends JpaRepository<DailyAggregate, Long> {
}
