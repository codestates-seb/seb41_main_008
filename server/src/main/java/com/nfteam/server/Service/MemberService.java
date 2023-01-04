package com.nfteam.server.Service;

import com.nfteam.server.Domain.Member;
import com.nfteam.server.Repository.MemberRepository;

import com.nfteam.server.dto.Request.MemberRegisterRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.NoSuchElementException;

@Getter
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String create(MemberRegisterRequest memberRegisterRequest){
        Member member=Member.builder()
                .userId(memberRegisterRequest.getUserId())
                .userPassword(passwordEncoder.encode(memberRegisterRequest.getPassword()))
                .nickname(memberRegisterRequest.getNickname())
                .build();

        return memberRepository.save(member).getUserId();
    }

    //edit 추후에 수정 예정


    public Member findUser(String MemberId){
        return memberRepository.findById(MemberId).orElseThrow(()->new NoSuchElementException("Invalid accountUD"));
    }
    //delete 로그아웃으로








}
