package com.nfteam.server.auth.service;

import com.nfteam.server.domain.member.entity.MemberPlatform;
import com.nfteam.server.dto.response.auth.SocialLoginResponse;
import com.nfteam.server.exception.auth.NotSupportedPlatformException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OAuth2Service {

    private final Map<String, OAuth2> oAuth2Map;
    private final List<OAuth2> oAuth2List;

    public OAuth2Service(Map<String, OAuth2> oAuth2Map, List<OAuth2> oAuth2List) {
        this.oAuth2Map = oAuth2Map;
        this.oAuth2List = oAuth2List;
    }

    public SocialLoginResponse login(String token, MemberPlatform memberPlatform) {
        switch (memberPlatform) {
            case GOOGLE:
                return oAuth2Map.get("googleOAuth2").proceedLogin(token);
            default:
                throw new NotSupportedPlatformException();
        }
    }

}