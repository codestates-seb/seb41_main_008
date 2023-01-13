package com.nfteam.server.auth.oauth2;

import com.nfteam.server.auth.jwt.JwtTokenizer;
import com.nfteam.server.auth.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
@Component
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenizer jwtTokenizer;
    private final RedisRepository redisRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        redirect(request, response, oAuth2User);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, OAuth2User oAuth2User) throws IOException {
        String accessToken = "Bearer " + jwtTokenizer.generateOAuthAccessToken(oAuth2User);
        String refreshToken = jwtTokenizer.generateRefreshToken();

        // 레디스 리프레시 토큰 저장
        redisRepository.saveRefreshToken(refreshToken, String.valueOf(oAuth2User.getAttributes().get("email")));
        String uri = createURI(accessToken, refreshToken).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private URI createURI(String accessToken, String refreshToken) {
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost:3000")
                .path("/oauth2/redirect")
                .queryParam(HttpHeaders.AUTHORIZATION, accessToken)
                .queryParam("RefreshToken", refreshToken)
                .build()
                .toUri();
    }
}
