package com.nfteam.server.member.repository;

import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.entity.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.memberId =:memberId and m.memberStatus =:status")
    Optional<Member> findByMemberIdAndMemberStatus(Long memberId, MemberStatus status);

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
