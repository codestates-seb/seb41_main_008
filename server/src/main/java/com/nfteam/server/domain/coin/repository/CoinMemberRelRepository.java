package com.nfteam.server.domain.coin.repository;

import com.nfteam.server.domain.coin.entity.Coin;
import com.nfteam.server.domain.coin.entity.CoinMemberRel;
import com.nfteam.server.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface CoinMemberRelRepository extends JpaRepository<CoinMemberRel, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
    Optional<CoinMemberRel> findByMemberAndCoin(Member member, Coin coin);

    @Query("select rel from CoinMemberRel rel " +
            "left join fetch rel.member " +
            "left join fetch rel.coin " +
            "where rel.member.memberId =:memberId")
    List<CoinMemberRel> findAllByMemberId(Long memberId);

}