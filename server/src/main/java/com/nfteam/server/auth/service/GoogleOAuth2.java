package com.nfteam.server.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.nfteam.server.common.utils.JwtTokenizer;
import com.nfteam.server.security.userdetails.MemberDetails;
import com.nfteam.server.dto.response.auth.GoogleToken;
import com.nfteam.server.dto.response.auth.GoogleUser;
import com.nfteam.server.dto.response.auth.SocialLoginResponse;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.entity.MemberPlatform;
import com.nfteam.server.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Transactional(readOnly = true)
public class GoogleOAuth2 implements OAuth2 {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String grantType;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final JwtTokenizer jwtTokenizer;

    public GoogleOAuth2(@Value("${oauth2.google.client-id}") String clientId,
                        @Value("${oauth2.google.client-secret}") String clientSecret,
                        @Value("${oauth2.google.redirect-uri}") String redirectUri,
                        MemberRepository memberRepository,
                        JwtTokenizer jwtTokenizer) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.grantType = "authorization_code";
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.memberRepository = memberRepository;
        this.jwtTokenizer = jwtTokenizer;
    }

    @Override
    public SocialLoginResponse proceedLogin(String code) {
        ResponseEntity<String> accessTokenResponse = createGetAccessTokenRequest(code);
        GoogleToken googleToken = getAccessToken(accessTokenResponse);

        ResponseEntity<String> userInfoResponse = createGetInfoRequest(googleToken);
        GoogleUser googleUser = getUserInfo(userInfoResponse);

        if (isFistLogin(googleUser)) {
            signUp(googleUser);
        }

        return makeSocialResponse(googleUser);
    }

    private ResponseEntity<String> createGetAccessTokenRequest(String code) {
        String url = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", grantType);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
    }

    private GoogleToken getAccessToken(ResponseEntity<String> accessTokenResponse) {
        GoogleToken googleToken = null;
        try {
            googleToken = objectMapper.readValue(accessTokenResponse.getBody(), GoogleToken.class);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("엑세스 토큰 json 변환에 실패하였습니다.");
        }
        return googleToken;
    }

    private ResponseEntity<String> createGetInfoRequest(GoogleToken googleToken) {
        String url = "https://www.googleapis.com/oauth2/v1/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + googleToken.getAccessToken());
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
    }

    private GoogleUser getUserInfo(ResponseEntity<String> userInfoResponse) {
        GoogleUser googleUser = null;
        try {
            googleUser = objectMapper.readValue(userInfoResponse.getBody(), GoogleUser.class);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("구글 사용자 정보 json 변환에 실패하였습니다.");
        }

        return googleUser;
    }

    private boolean isFistLogin(GoogleUser googleUser) {
        return !memberRepository.existsByEmail(googleUser.getEmail());
    }

    @Transactional
    public void signUp(GoogleUser googleUser) {
        Member member = new Member(googleUser.getEmail(), googleUser.getName(), MemberPlatform.GOOGLE);
        memberRepository.save(member);
    }

    @Transactional
    public SocialLoginResponse makeSocialResponse(GoogleUser googleUser) {
        Member member = memberRepository.findByEmail(googleUser.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(googleUser.getEmail()));
        member.updateNickname(googleUser.getName());

        MemberDetails memberDetails = new MemberDetails(member);
        String accessToken = "Bearer " + jwtTokenizer.generateAccessToken(memberDetails);
        String refreshToken = jwtTokenizer.generateRefreshToken();

        return new SocialLoginResponse(accessToken, refreshToken, member);
    }

}
