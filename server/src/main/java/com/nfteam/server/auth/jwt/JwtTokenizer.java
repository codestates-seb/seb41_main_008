package com.nfteam.server.auth.jwt;

import com.nfteam.server.auth.repository.RedisRepository;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
public class JwtTokenizer {
    private final String secretKey;
    private final int accessTokenExpirationMinutes;
    private final int refreshTokenExpirationMinutes;

//    private final RedisRepository redisRepository;


    public JwtTokenizer(@Value("${jwt.secret-key}") String secretKey,
                        @Value("${jwt.access-token-expiration-minutes}") int accessTokenExpirationMinutes,
                        @Value("${jwt.refresh-token-expiration-minutes}") int refreshTokenExpirationMinutes) {
        this.secretKey = secretKey;
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
        this.refreshTokenExpirationMinutes = refreshTokenExpirationMinutes;
//        this.redisRepository = redisRepository;
    }

    public String generateAccessToken(Map<String, Object> claims,
                                      String subject, Date expiration, String base64EncodedSecretKey) {

        Key key = getKeyFromBase64EncodedKey(secretKey);
        return null;
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        //바이트로 변환된 secretkey를 key로 변환
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

}
