package com.nfteam.server.member.service;

import com.nfteam.server.exception.BusinessLogicException;
import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
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

    public Member createMember(Member member) {
        //존재 여부 확인
        verifyExistsEmail(member.getEmail());

        //pw 암호화
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        //TODO : role, 기본 프로필사진 설정
        member.setProfileUrl("temp");

        return memberRepository.save(member);
    }

    /**
     * 도구
     */
    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()){
            //TODO exeption 글로벌 처리하기
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
}
