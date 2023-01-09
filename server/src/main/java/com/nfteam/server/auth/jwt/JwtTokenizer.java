package com.nfteam.server.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


@Getter
@Component
public class JwtTokenizer {

    private final SecretKey secretKey;

    private final int accessTokenExpirationMinutes;

    private final int refreshTokenExpirationMinutes;


    public JwtTokenizer(@Value("${jwt.secret-key}") String secretKey,
                        @Value("${jwt.access-token-expiration-minutes}") int accessTokenExpirationMinutes,
                        @Value("${jwt.refresh-token-expiration-minutes}") int refreshTokenExpirationMinutes) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
        this.refreshTokenExpirationMinutes = refreshTokenExpirationMinutes;
    }

    public String generateAccessToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(getTokenExpiration(accessTokenExpirationMinutes))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(getTokenExpiration(refreshTokenExpirationMinutes))
                .signWith(secretKey)
                .compact();
    }

    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    //JWT에 포함되어 있는 Signature를 검증함으로써 JWT의 위/변조 여부를 확인
    public Jws<Claims> getClaims(String jws) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jws);
    }

    // 토큰 유효기간 검사
    public boolean isValidDateToken(String jws) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jws);
            Date exp = claims.getBody().getExpiration();
            return exp.after(new Date());
        } catch (JwtException je) {
            return false;
        }
    }
}
