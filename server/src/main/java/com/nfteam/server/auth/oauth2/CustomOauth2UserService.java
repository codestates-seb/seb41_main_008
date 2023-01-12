package com.nfteam.server.auth.oauth2;

import com.nfteam.server.dto.request.auth.OAuthMemberProfile;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest); //각 Oauth 사에서 받은 유저 정보
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributes = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // Oauth2request PK 호출

        Map<String, Object> attributes = oAuth2User.getAttributes(); //Oauth 서비스의 유저 정보
        OAuthMemberProfile oAuthMemberProfile = OAuthAttributes.extract(registrationId, attributes);

        Member member = createOrUpdateMember(oAuthMemberProfile);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getMemberRole().getValue())),
                attributes, userNameAttributes);
    }

    @Transactional
    public Member createOrUpdateMember(OAuthMemberProfile oAuthMemberProfile) {
        Member member;
        String email = oAuthMemberProfile.getEmail();
        // 기존회원
        if (memberRepository.existsByEmail(email)) {
            member = memberRepository.findByEmail(email).get();
            member.updateMemberPlatform(oAuthMemberProfile.getMemberPlatform());
        } else {
            // 신규회원
            member = new Member(email, oAuthMemberProfile.getNickname(), oAuthMemberProfile.getMemberPlatform());
            memberRepository.save(member);
        }
        return member;
    }

}
