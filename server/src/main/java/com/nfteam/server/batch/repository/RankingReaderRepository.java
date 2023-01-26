package com.nfteam.server.batch.repository;

import com.nfteam.server.transaction.entity.TransAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface RankingReaderRepository extends JpaRepository<TransAction, Long> {

    @Query("select t from TransAction t " +
            "where t.createdDate >:localDateTime " +
            "group by t.collection " +
            "order by count(t.item) desc")
    Page<TransAction> getRankingByTime(LocalDateTime localDateTime, Pageable pageable);

    @Query("select t from TransAction t " +
            "where t.createdDate >:localDateTime " +
            "and t.coin.coinId =:coinId " +
            "group by t.collection " +
            "order by count(t.item) desc")
    Page<TransAction> getRankingByCoin(LocalDateTime localDateTime, Long coinId, Pageable pageable);

}