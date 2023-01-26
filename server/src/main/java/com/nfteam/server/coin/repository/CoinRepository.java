package com.nfteam.server.coin.repository;

import com.nfteam.server.coin.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    Optional<Coin> findByCoinName(String coinName);

}

