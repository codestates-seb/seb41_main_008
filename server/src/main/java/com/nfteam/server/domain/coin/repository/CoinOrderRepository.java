package com.nfteam.server.domain.coin.repository;

import com.nfteam.server.domain.coin.entity.CoinOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CoinOrderRepository extends JpaRepository<CoinOrder, Long> {

    @Query("select c from CoinOrder c " +
            "left join fetch c.buyer " +
            "left join fetch c.coin " +
            "where c.tid =:tid")
    Optional<CoinOrder> findByTidWithBuyer(String tid);

}