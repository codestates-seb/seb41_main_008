package com.nfteam.server.auth.jwt;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.exception.token.TokenNotValidateException;
import com.nfteam.server.member.entity.MemberRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
                (String) claims.get("email"),
                (String) claims.get("nickname"),
                (String) claims.get("role")
        );
        return new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
    }

    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
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


    public String generateOAuthAccessToken(OAuth2User oAuth2User) {
        return Jwts.builder()
                .claim("email", String.valueOf(oAuth2User.getAttributes().get("email")))
                .claim("nickname", String.valueOf(oAuth2User.getAttributes().get("name")))
                .claim("role", MemberRole.USER)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(getTokenExpiration(accessTokenExpirationMinutes))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
