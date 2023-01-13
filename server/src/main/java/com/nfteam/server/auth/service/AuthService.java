package com.nfteam.server.auth.service;

import com.nfteam.server.auth.jwt.JwtTokenizer;
import com.nfteam.server.auth.repository.RedisRepository;
import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.exception.token.RefreshTokenExpiredException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final RedisRepository redisRepository;
    private final JwtTokenizer jwtTokenizer;
    private final RedisTemplate<String, String> redisTemplate;

    public void logout(String refreshToken) {
        redisRepository.expireRefreshToken(refreshToken);
    }

    public String reissue(String refreshToken) {
        // 1차 서버 - 리프레시 토큰 유효기한 검사
        Boolean isValidDate = jwtTokenizer.isValidDateToken(refreshToken);

        // 2차 - 레디스 리프레시 토큰 존재여부 검사
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Boolean hasKey = redisTemplate.hasKey(refreshToken);

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