package com.nfteam.server.coin.repository;

import com.nfteam.server.coin.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin,Long> {

}
