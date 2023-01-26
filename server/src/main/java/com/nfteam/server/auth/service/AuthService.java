package com.nfteam.server.auth.service;

import com.nfteam.server.common.utils.JwtTokenizer;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.exception.token.RefreshTokenExpiredException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import com.nfteam.server.redis.repository.RedisRepository;
import com.nfteam.server.security.userdetails.MemberDetails;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final RedisRepository redisRepository;
    private final JwtTokenizer jwtTokenizer;
    private final RedisTemplate<String, String> redisTemplate;

    public AuthService(MemberRepository memberRepository, RedisRepository redisRepository, JwtTokenizer jwtTokenizer, RedisTemplate<String, String> redisTemplate) {
        this.memberRepository = memberRepository;
        this.redisRepository = redisRepository;
        this.jwtTokenizer = jwtTokenizer;
        this.redisTemplate = redisTemplate;
    }

    public void login(String refreshToken, String email) {
        redisRepository.saveRefreshToken(refreshToken, email);
    }

    public void logout(String refreshToken) {
        redisRepository.expireRefreshToken(refreshToken);
    }

    public String reissue(String refreshToken) {
        // 1차 서버 - 리프레시 토큰 유효기한 검사
        Boolean isValidDate = jwtTokenizer.isValidDateToken(refreshToken);

        // 2차 - 레디스 리프레시 토큰 존재여부 검사
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Boolean hasKey = redisTemplate.hasKey(refreshToken);

        // 레디스 토큰 유효성 검사 통과 시 엑세스 토큰 재 발급
        if (isValidDate && hasKey) {
            String email = valueOperations.get(refreshToken);
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new MemberNotFoundException(email));
            return jwtTokenizer.generateAccessToken(new MemberDetails(member));
        } else {
            throw new RefreshTokenExpiredException();
        }
    }

}