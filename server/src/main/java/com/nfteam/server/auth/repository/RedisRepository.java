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

    public void saveRefresh(String refreshToken, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken, value, jwtTokenizer.getRefreshTokenExpirationMinutes(), TimeUnit.MINUTES);
    }

    public void expireRefreshToken(String key) {
        redisTemplate.delete(key);
        //redisTemplate.opsForValue().getAndExpire(key,0, TimeUnit.MINUTES);
    }

    public boolean hasRefresh(String refresh) {
        boolean haskey = Boolean.TRUE.equals(redisTemplate.hasKey(refresh));
        return haskey;
    }


}
