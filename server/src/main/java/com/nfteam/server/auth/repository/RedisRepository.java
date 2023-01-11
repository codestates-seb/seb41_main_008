package com.nfteam.server.auth.repository;

import com.nfteam.server.auth.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenizer jwtTokenizer;

    public void saveRefreshToken(String memberId, String refreshToken) {
        // refreshToken - memberId(pk) K-V 형태로 레디스 저장
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken, memberId, jwtTokenizer.getRefreshTokenExpirationMinutes(), TimeUnit.MINUTES);
    }

    public void expireRefreshToken(String key) {
        // refreshToken 의 key = memberId(pk)
        redisTemplate.delete(key);
    }


}
