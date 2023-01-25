package com.nfteam.server.dto.response.auth;

import com.nfteam.server.member.entity.Member;
import lombok.Getter;

@Getter
public class SocialLoginResponse {

    private String accessToken;
    private String refreshToken;
    private LoginResponse loginResponse;

    public SocialLoginResponse(String accessToken, String refreshToken, Member member) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.loginResponse = LoginResponse.of(member);
    }

}