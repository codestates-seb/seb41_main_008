package com.nfteam.server.auth.utils;

import com.nfteam.server.exception.token.TokenNotValidateException;
import com.nfteam.server.security.userdetails.MemberDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

@Component
@Getter
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

    public String generateAccessToken(MemberDetails memberDetails) {
        return Jwts.builder()
                .claim("memberId", memberDetails.getMemberId())
                .claim("email", memberDetails.getEmail())
                .claim("nickname", memberDetails.getNickname())
                .claim("role", memberDetails.getRole())
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(getTokenExpiration(accessTokenExpirationMinutes))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(getTokenExpiration(refreshTokenExpirationMinutes))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = getClaims(accessToken);
        String role = (String) claims.get("role");
        if (role == null) {
            throw new TokenNotValidateException("토큰에 권한 정보가 존재하지 않습니다.");
        }
        MemberDetails memberDetails = new MemberDetails(
                String.valueOf(claims.get("memberId")),
                (String) claims.get("email"),
                (String) claims.get("nickname"),
                (String) claims.get("role")
        );
        return new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
    }

    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        return calendar.getTime();
    }

    public Claims getClaims(String jws) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jws)
                .getBody();
    }

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