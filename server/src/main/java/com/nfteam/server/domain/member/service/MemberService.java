package com.nfteam.server.domain.member.service;

import com.nfteam.server.domain.member.entity.Member;
import com.nfteam.server.domain.member.entity.MemberStatus;
import com.nfteam.server.domain.member.repository.MemberRepository;
import com.nfteam.server.dto.request.member.MemberCreateRequest;
import com.nfteam.server.dto.request.member.MemberPatchRequest;
import com.nfteam.server.exception.auth.NotAuthorizedException;
import com.nfteam.server.exception.member.MemberEmailExistException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        Optional.ofNullable(memberPatchRequest.getDescription())
                .ifPresent(desc -> findMember.updateDescription(desc));
        Optional.ofNullable(memberPatchRequest.getProfileImageName())
                .ifPresent(profileImageName -> findMember.updateProfileImg(profileImageName));
        Optional.ofNullable(memberPatchRequest.getBannerImageName())
                .ifPresent(bannerImageName -> findMember.updateBannerImg(bannerImageName));
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

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .filter(m -> !m.getMemberStatus().equals(MemberStatus.MEMBER_QUIT))
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    @Transactional
    public void deleteMember(Long memberId, String email) {
        Member findMember = findVerifiedMember(memberId, email);
        findMember.updateMemberStatusQuit();
    }

    @Transactional
    public void updateMemberStatus() {
        LocalDateTime localDateTimeBefore = LocalDateTime.now().minusMonths(6);
        memberRepository.updateSleepStatus(localDateTimeBefore, MemberStatus.MEMBER_SLEEP);
    }

}