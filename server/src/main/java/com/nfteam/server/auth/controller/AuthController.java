package com.nfteam.server.auth.controller;

import com.nfteam.server.auth.service.AuthService;
import com.nfteam.server.auth.service.OAuth2Service;
import com.nfteam.server.dto.response.auth.LoginResponse;
import com.nfteam.server.dto.response.auth.SocialLoginResponse;
import com.nfteam.server.member.entity.MemberPlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final OAuth2Service oAuth2Service;
    private final AuthService authService;

    @GetMapping("/login/{socialType}")
    public ResponseEntity<LoginResponse> oauthLogin(@PathVariable(name = "socialType") String socialType,
                                                    @RequestParam String socialToken) {
        MemberPlatform memberPlatform = MemberPlatform.valueOf(socialType.toUpperCase());
        SocialLoginResponse socialLoginResponse = oAuth2Service.login(socialToken, memberPlatform);

        String email = socialLoginResponse.getLoginResponse().getEmail();
        String accessToken = socialLoginResponse.getAccessToken();
        String refreshToken = socialLoginResponse.getRefreshToken();
        authService.login(refreshToken, email);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        headers.add("RefreshToken", refreshToken);

        return new ResponseEntity<>(socialLoginResponse.getLoginResponse(), headers, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "RefreshToken") String refreshToken) {
        authService.logout(refreshToken);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/reissue")
    public ResponseEntity<Void> reissue(@RequestHeader(value = "RefreshToken") String refreshToken) {
        String reissuedAccessToken = authService.reissue(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedAccessToken)
                .build();
    }

}