package com.nfteam.server.auth.service;

import com.nfteam.server.auth.jwt.JwtTokenizer;
import com.nfteam.server.auth.repository.RedisRepository;
import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.exception.token.RefreshTokenExpiredException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisRepository redisRepository;

    private final JwtTokenizer jwtTokenizer;

    public void logout(HttpServletRequest request, long memberId) {
        String refreshToken = request.getHeader("RefreshToken");
        redisRepository.expireRefreshToken(refreshToken);
    }

    public String reissue(MemberDetails memberDetails, String refresh){
        if(redisRepository.hasRefresh(refresh)){
            Map<String, Object> claims = new HashMap<>();

            claims.put("memberId", memberDetails.getMemberId());
            claims.put("username", memberDetails.getEmail());
            claims.put("roles", memberDetails.getRoles());
            String subject = memberDetails.getEmail();

            Date expiration = jwtTokenizer.getTokenExpiration(
                jwtTokenizer.getAccessTokenExpirationMinutes());

            String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
                jwtTokenizer.getSecretKey());

            String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration,
                base64EncodedSecretKey);

            return accessToken;
        }else {
            throw new RefreshTokenExpiredException();
        }
    }
}
