package com.nfteam.server.auth.service;

import com.nfteam.server.dto.response.auth.SocialLoginResponse;
import com.nfteam.server.exception.auth.NotSupportedPlatformException;
import com.nfteam.server.domain.member.entity.MemberPlatform;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {

    private final GoogleOAuth2 googleOAuth2;

    public OAuth2Service(GoogleOAuth2 googleOAuth2) {
        this.googleOAuth2 = googleOAuth2;
    }

    public SocialLoginResponse login(String token, MemberPlatform memberPlatform) {
        switch (memberPlatform) {
            case GOOGLE:
                return googleOAuth2.proceedLogin(token);
            default:
                throw new NotSupportedPlatformException();
        }
    }

}