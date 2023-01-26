package com.nfteam.server.member.repository;

import com.nfteam.server.member.entity.Member;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
    List<Member> findAllByLastLoginTimeBefore(LocalDateTime time);

}