package com.nfteam.server.domain.member.repository;

import com.nfteam.server.domain.member.entity.Member;
import com.nfteam.server.domain.member.entity.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.memberStatus =:memberStatus where m.lastLoginTime <:localDateTimeBefore")
    void updateSleepStatus(LocalDateTime localDateTimeBefore, MemberStatus memberStatus);

}