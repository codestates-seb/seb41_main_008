package com.nfteam.server.auth.service;

import com.nfteam.server.dto.response.auth.SocialLoginResponse;
import com.nfteam.server.exception.auth.NotSupportedPlatformException;
import com.nfteam.server.member.entity.MemberPlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final GoogleOAuth2 googleOAuth2;

    public SocialLoginResponse login(String token, MemberPlatform memberPlatform) {
        switch (memberPlatform) {
            case GOOGLE:
                return googleOAuth2.proceedLogin(token);
            default:
                throw new NotSupportedPlatformException();
        }
    }

}