package com.nfteam.server.member.service;

import com.nfteam.server.dto.request.member.MemberPatchDto;
import com.nfteam.server.dto.request.member.MemberPostDto;
import com.nfteam.server.exception.auth.NotAuthorizedException;
import com.nfteam.server.exception.member.MemberEmailExistException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
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
    public Member createMember(MemberPostDto memberPostDto) {
        verifyExistsEmail(memberPostDto.getEmail());
        String encryptedPassword = passwordEncoder.encode(memberPostDto.getPassword());
        Member member = new Member(memberPostDto.getEmail(), encryptedPassword, memberPostDto.getNickname());
        return memberRepository.save(member);
    }

    @Transactional
    public Member updateMember(MemberPatchDto memberPatchDto, Long memberId, String email) {
        Member findMember = findVerifiedMember(memberId, email);
        Optional.ofNullable(memberPatchDto.getNickname())
                .ifPresent(name -> findMember.updateNickname(name));
        Optional.ofNullable(memberPatchDto.getProfileImageName())
                .ifPresent(profileImageName -> findMember.updateProfileImg(profileImageName));
        return findMember;
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() ->
                new MemberNotFoundException(memberId));
    }

    @Transactional
    public void deleteMember(Long memberId, String email) {
        Member findMember = findVerifiedMember(memberId, email);
        findMember.updateMemberStatusQuit();
    }

    private void verifyExistsEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberEmailExistException(email);
        }
    }

    public Member findVerifiedMember(Long memberId, String email) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberNotFoundException(memberId));

        if (findMember.getEmail().equals(email)) {
            return findMember;
        } else {
            throw new NotAuthorizedException();
        }
    }
}