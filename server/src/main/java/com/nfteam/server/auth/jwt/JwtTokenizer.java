package com.nfteam.server.auth.jwt;

import com.nfteam.server.auth.repository.RedisRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Getter
@Component
public class JwtTokenizer {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;



    public String generateAccessToken(Map<String, Object> claims,
        String subject, Date expiration, String base64EncodedSecretKey){

        Key key = getKeyFromBase64EncodedKey(secretKey);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Calendar.getInstance().getTime())
            .setExpiration(expiration)
            .signWith(key)
            .compact();
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        //바이트로 변환된 secretkey를 key로 변환
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

}
