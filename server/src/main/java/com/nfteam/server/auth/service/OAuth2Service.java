package com.nfteam.server.auth.service;

import com.nfteam.server.domain.member.entity.MemberPlatform;
import com.nfteam.server.dto.response.auth.SocialLoginResponse;
import com.nfteam.server.exception.auth.NotSupportedPlatformException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OAuth2Service {

    private final Map<String, OAuth2> oAuth2Map;

    public OAuth2Service(Map<String, OAuth2> oAuth2Map) {
        this.oAuth2Map = oAuth2Map;
    }

    // 각 플랫폼 - OAuth2 인터페이스 구현 클래스 호출 후 로그인 프로세스 진행
    public SocialLoginResponse login(String token, MemberPlatform memberPlatform) {
        switch (memberPlatform) {
            case GOOGLE:
                return oAuth2Map.get("googleOAuth2").proceedLogin(token);
            default:
                throw new NotSupportedPlatformException();
        }
    }

}