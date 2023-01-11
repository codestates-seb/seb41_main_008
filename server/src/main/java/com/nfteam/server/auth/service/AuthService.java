package com.nfteam.server.auth.service;

import com.nfteam.server.auth.jwt.JwtTokenizer;
import com.nfteam.server.auth.repository.RedisRepository;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.exception.token.RefreshTokenExpiredException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisRepository redisRepository;

    private final JwtTokenizer jwtTokenizer;

    private final RedisTemplate<String, String> redisTemplate;

    private final MemberRepository memberRepository;

    public void logout(Long memberId) {
        redisRepository.expireRefreshToken(String.valueOf(memberId));
    }

    public String reissue(String refreshToken) {
        // 1차로 서버에서 리프레시 토큰 유효기한 검사
        boolean isValidDate = jwtTokenizer.isValidDateToken(refreshToken);

        // 2차로 레디스에서 리프레시 토큰 존재여부 검사
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Boolean hasKey = redisTemplate.hasKey(refreshToken);

        if (isValidDate && hasKey) {
            String id = valueOperations.get(refreshToken);

            long memberId = Long.parseLong(id);
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new MemberNotFoundException(memberId));

            Map<String, Object> claims = new HashMap<>();
            claims.put("memberId", member.getMemberId());
            claims.put("username", member.getEmail());
            claims.put("roles", member.getRoles());
            String subject = member.getEmail();

            return jwtTokenizer.generateAccessToken(claims, subject);
        } else {
            throw new RefreshTokenExpiredException();
        }
    }
}