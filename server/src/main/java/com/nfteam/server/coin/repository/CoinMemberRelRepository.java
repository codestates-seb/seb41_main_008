package com.nfteam.server.coin.repository;

import com.nfteam.server.coin.entity.CoinMemberRel;
import com.nfteam.server.dto.response.coin.CoinMemberRelResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoinMemberRelRepository extends JpaRepository<CoinMemberRel,Long> {

    Optional<CoinMemberRel> findByCoin(Long coinname);

    Optional<CoinMemberRel> findByMember(Long memberId);

    List<CoinMemberRelResponse> findCoinMemberRelByMember(Long memberId);
}
