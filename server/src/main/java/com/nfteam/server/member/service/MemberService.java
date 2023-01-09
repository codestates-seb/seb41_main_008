package com.nfteam.server.member.service;

import com.nfteam.server.auth.repository.RedisRepository;
import com.nfteam.server.auth.utils.CustomAuthorityUtils;
import com.nfteam.server.exception.member.MemberEmailExistException;
import com.nfteam.server.member.entity.Member;
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
    private final RedisRepository redisRepository;


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

    public void logout(HttpServletRequest request, long memberId) {
        String refreshToken = request.getHeader("RefreshToken");
        redisRepository.expireRefreshToken(refreshToken);
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


}
