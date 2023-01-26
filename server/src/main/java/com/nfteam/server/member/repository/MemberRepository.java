package com.nfteam.server.member.repository;

import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.entity.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying(flushAutomatically = true)
    @Query("update Member m set m.memberStatus =:memberStatus where m.lastLoginTime <:localDateTimeBefore")
    void updateSleepStatus(LocalDateTime localDateTimeBefore, MemberStatus memberStatus);

}