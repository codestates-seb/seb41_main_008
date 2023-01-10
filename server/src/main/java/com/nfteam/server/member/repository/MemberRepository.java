package com.nfteam.server.member.repository;

import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.entity.Member.MemberStatus;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m left join fetch m.roles r where m.email =:email")
    Optional<Member> findByEmail(String email);

    @Query("select m from Member m left join fetch m.roles r where m.memberId =:memberId and m.memberStatus =:status")
    Optional<Member> findByMemberIdAndMemberStatus(Long memberId, MemberStatus status);

    @Override
    @Query("select m from Member m left join fetch m.roles r where m.memberId =:memberId")
    Optional<Member> findById(Long memberId);
}
