package com.nfteam.server.coin.repository;

import com.nfteam.server.coin.entity.Coin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoinRepository extends JpaRepository<Coin,Long> {
    Optional<Coin> findByCoinName(String coinName);

    Optional<Coin> findByCurrentPrice(String coinName);

    Optional<Coin> findByCoinId(Long coinId);
}
