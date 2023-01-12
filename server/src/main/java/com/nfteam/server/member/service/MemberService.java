package com.nfteam.server.member.service;

import com.nfteam.server.exception.auth.NotAuthorizedException;
import com.nfteam.server.exception.member.MemberEmailExistException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.entity.MemberStatus;
import com.nfteam.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.updateCreateInfo(encryptedPassword);
        return memberRepository.save(member);
    }

    @Transactional
    public Member updateMember(Member member, String email, Long memberId) {
        Member findMember = findVerifiedMember(memberId, email);
        Optional.ofNullable(member.getNickname())
                .ifPresent(name -> findMember.updateNickname(name));
        Optional.ofNullable(member.getProfileImage())
                .ifPresent(profileImage -> findMember.updateProfileImg(profileImage));
        return findMember;
    }

    public Member findMember(long memberId, String email) {
        if (!email.isEmpty()) {
            return findVerifiedMember(memberId, email);
        }
        return memberRepository.findByMemberIdAndMemberStatus(memberId, MemberStatus.MEMBER_ACTIVE)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    @Transactional
    public void deleteMember(int memberId, String email) {
        Member findMember = findVerifiedMember(memberId, email);
        findMember.updateMemberStatusQuit();
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            throw new MemberEmailExistException(email);
        }
    }

    public Member findVerifiedMember(long memberId, String email) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() ->
                new MemberNotFoundException(memberId));

        if (findMember.getEmail().equals(email)) {
            return findMember;
        } else {
            throw new NotAuthorizedException();
        }
    }
}