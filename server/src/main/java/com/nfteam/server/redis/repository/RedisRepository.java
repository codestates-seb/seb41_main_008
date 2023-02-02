package com.nfteam.server.redis.repository;

import com.nfteam.server.auth.utils.JwtTokenizer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenizer jwtTokenizer;

    public RedisRepository(RedisTemplate<String, String> redisTemplate, JwtTokenizer jwtTokenizer) {
        this.redisTemplate = redisTemplate;
        this.jwtTokenizer = jwtTokenizer;
    }

    public void saveRefreshToken(String refreshToken, String email) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken, email, jwtTokenizer.getRefreshTokenExpirationMinutes(), TimeUnit.MINUTES);
    }

    public void expireRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

}