package com.nfteam.server.domain.coin.repository;

import com.nfteam.server.domain.coin.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    Optional<Coin> findByCoinName(String coinName);

}