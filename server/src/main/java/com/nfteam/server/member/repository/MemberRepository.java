package com.nfteam.server.member.repository;

import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.entity.Member.MemberStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByMemberIdAndMemberStatus(Long memberId, MemberStatus status);

    Boolean existsByEmail(String email);


}
