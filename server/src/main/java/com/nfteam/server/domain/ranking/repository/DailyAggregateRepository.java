package com.nfteam.server.domain.ranking.repository;

import com.nfteam.server.domain.ranking.entity.DailyAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyAggregateRepository extends JpaRepository<DailyAggregate, Long> {

    @Query("select da from DailyAggregate da where da.collection.collectionId =:collectionId and da.baseDate =:localDate")
    Optional<DailyAggregate> findDailyAggregateByCollection(Long collectionId, LocalDate localDate);

}
