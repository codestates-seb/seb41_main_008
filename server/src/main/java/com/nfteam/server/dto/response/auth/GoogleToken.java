package com.nfteam.server.dto.response.auth;

import lombok.Getter;

@Getter
public class GoogleToken {

    private String accessToken;
    private String expiresIn;
    private String idToken;
    private String refreshToken;
    private String scope;
    private String tokenType;

    public GoogleToken() {
    }

    public GoogleToken(String accessToken,
                       String expiresIn,
                       String idToken,
                       String refreshToken,
                       String scope,
                       String tokenType) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.idToken = idToken;
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.tokenType = tokenType;
    }

}
