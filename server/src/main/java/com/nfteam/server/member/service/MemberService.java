package com.nfteam.server.member.service;

import com.nfteam.server.dto.request.member.MemberCreateRequest;
import com.nfteam.server.dto.request.member.MemberPatchRequest;
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
    public Long createMember(MemberCreateRequest memberCreateRequest) {
        verifyExistsEmail(memberCreateRequest.getEmail());
        String encryptedPassword = passwordEncoder.encode(memberCreateRequest.getPassword());
        Member member = new Member(memberCreateRequest.getEmail(), encryptedPassword, memberCreateRequest.getNickname());
        return memberRepository.save(member).getMemberId();
    }

    private void verifyExistsEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberEmailExistException(email);
        }
    }

    @Transactional
    public Long updateMember(MemberPatchRequest memberPatchRequest, Long memberId, String email) {
        Member findMember = findVerifiedMember(memberId, email);
        Optional.ofNullable(memberPatchRequest.getNickname())
                .ifPresent(name -> findMember.updateNickname(name));
        Optional.ofNullable(memberPatchRequest.getProfileImageName())
                .ifPresent(profileImageName -> findMember.updateProfileImg(profileImageName));
        Optional.ofNullable(memberPatchRequest.getBannerImageName())
                .ifPresent(bannerImageName -> findMember.updateProfileImg(bannerImageName));
        return findMember.getMemberId();
    }

    public Member findVerifiedMember(Long memberId, String email) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        if (findMember.getEmail().equals(email)) {
            return findMember;
        } else {
            throw new NotAuthorizedException();
        }
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    @Transactional
    public void deleteMember(Long memberId, String email) {
        Member findMember = findVerifiedMember(memberId, email);
        findMember.updateMemberStatusQuit();
    }




}