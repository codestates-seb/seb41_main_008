package com.nfteam.server.auth.service;

import com.nfteam.server.auth.jwt.JwtTokenizer;
import com.nfteam.server.auth.repository.RedisRepository;
import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.exception.token.RefreshTokenExpiredException;
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

    public void logout(Long memberId) {
        redisRepository.expireRefreshToken(String.valueOf(memberId));
    }

    public String reissue(MemberDetails memberDetails, String refreshToken) {
        // 1차로 서버에서 리프레시 토큰 유효기한 검사
        boolean isValidDate = jwtTokenizer.isValidDateToken(refreshToken);

        // 2차로 레디스에서 리프레시 토큰 존재여부 검사
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String refreshTokenKey = String.valueOf(memberDetails.getMemberId());
        String savedToken = valueOperations.get(refreshTokenKey);
        boolean isEqual = savedToken.equals(refreshToken);

        if (isValidDate && isEqual) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("memberId", memberDetails.getMemberId());
            claims.put("username", memberDetails.getEmail());
            claims.put("roles", memberDetails.getRoles());
            String subject = memberDetails.getEmail();

            return jwtTokenizer.generateAccessToken(claims, subject);
        } else {
            throw new RefreshTokenExpiredException();
        }
    }
}