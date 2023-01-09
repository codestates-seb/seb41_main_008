package com.nfteam.server.member.service;

import com.nfteam.server.auth.repository.RedisRepository;
import com.nfteam.server.auth.utils.CustomAuthorityUtils;
import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.auth.NotAuthorizedException;
import com.nfteam.server.exception.member.MemberEmailExistException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.entity.Member.MemberStatus;
import com.nfteam.server.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;



    public Member createMember(Member member) {
        //존재 여부 확인
        verifyExistsEmail(member.getEmail());

        //pw 암호화
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        //TODO : role, 기본 프로필사진 설정
        member.setProfileUrl("temp");

        return memberRepository.save(member);
    }

    public Member updateMember(Member member, String email) {
        //TODO:비밀번호 수정 로직 리팩토링
        Member findMember = findVerifiedMember(member.getMemberId(), email);

        Optional.ofNullable(member.getNickname())
            .ifPresent(name -> findMember.setNickname(name));
        Optional.ofNullable(member.getMemberStatus())
            .ifPresent(memberStatus -> findMember.setMemberStatus(memberStatus));
        Optional.ofNullable(member.getPassword())
            .ifPresent(password -> findMember.setPassword(password));

        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId, String email) {
        if (!email.isEmpty()) {
            return findVerifiedMember(memberId, email);
        }
        return memberRepository.findByMemberIdAndMemberStatus(memberId, MemberStatus.MEMBER_ACTIVE).orElseThrow(
            () -> new MemberNotFoundException(memberId));
    }

    public void deleteMember(int memberId, String email) {
        Member findMember = findVerifiedMember(memberId,email);

        findMember.setMemberStatus(MemberStatus.MEMBER_QUIT);
        findMember.setEmail("");
        findMember.setNickname("");
    }

    /**
     * 도구
     */
    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            throw new MemberEmailExistException(email);
        }
    }

    public Member findVerifiedMember(long memberId, String email) {
        Optional<Member> optionalMember =
            memberRepository.findById(memberId);
        Member findMember =
            optionalMember.orElseThrow(() ->
                new MemberNotFoundException(memberId));
        if (findMember.getEmail().equals(email)) {
            return findMember;
        } else {
            throw new NotAuthorizedException();
        }
    }


}
