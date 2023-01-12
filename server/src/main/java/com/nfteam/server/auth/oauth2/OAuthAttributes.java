package com.nfteam.server.auth.oauth2;

import com.nfteam.server.dto.request.auth.OAuthMemberProfile;
import com.nfteam.server.member.entity.MemberPlatform;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", (attributes) -> new OAuthMemberProfile((String) attributes.get("email"),
            (String) attributes.get("name"), MemberPlatform.GOOGLE)),
    KAKAO("kakao", (attributes) -> {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        return new OAuthMemberProfile((String) kakaoAccount.get("email"), (String) kakaoProfile.get("nickname"),
                MemberPlatform.KAKAO);
    });

    private final String registrationId;
    private final Function<Map<String, Object>, OAuthMemberProfile> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, OAuthMemberProfile> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static OAuthMemberProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
