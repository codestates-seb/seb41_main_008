package com.nfteam.server.auth.controller;

import com.nfteam.server.auth.service.AuthService;
import com.nfteam.server.auth.service.OAuth2Service;
import com.nfteam.server.cart.service.CartService;
import com.nfteam.server.dto.response.auth.LoginResponse;
import com.nfteam.server.dto.response.auth.SocialLoginResponse;
import com.nfteam.server.member.entity.MemberPlatform;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final OAuth2Service oAuth2Service;
    private final AuthService authService;
    private final CartService cartService;

    public AuthController(OAuth2Service oAuth2Service, AuthService authService, CartService cartService) {
        this.oAuth2Service = oAuth2Service;
        this.authService = authService;
        this.cartService = cartService;
    }

    @GetMapping("/login/{socialType}")
    public ResponseEntity<LoginResponse> oauthLogin(@PathVariable(name = "socialType") String socialType,
                                                    @RequestHeader(value = "googleToken") String token) {
        // OAuth 로그인 진행
        SocialLoginResponse socialLoginResponse
                = oAuth2Service.login(token, MemberPlatform.valueOf(socialType.toUpperCase()));

        // Jwt 추출
        String accessToken = socialLoginResponse.getAccessToken();
        String refreshToken = socialLoginResponse.getRefreshToken();

        return new ResponseEntity<>(
                getLoginResponse(socialLoginResponse.getLoginResponse(), refreshToken),
                getHeaders(accessToken, refreshToken), HttpStatus.OK);
    }

    private LoginResponse getLoginResponse(LoginResponse loginResponse, String refreshToken) {
        // 레디스 서버 로그인 정보 저장
        String email = loginResponse.getEmail();
        authService.login(refreshToken, email);

        // 회원 활성 장바구니 정보 로그인 Response 추가
        loginResponse.addCart(cartService.loadOwnCart(email));

        return loginResponse;
    }

    private static HttpHeaders getHeaders(String accessToken, String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        headers.add("RefreshToken", refreshToken);
        return headers;
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "RefreshToken") String refreshToken) {
        authService.logout(refreshToken);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/reissue")
    public ResponseEntity<Void> reissue(@RequestHeader(value = "RefreshToken") String refreshToken) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authService.reissue(refreshToken))
                .build();
    }

}