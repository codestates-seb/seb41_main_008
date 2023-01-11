package com.nfteam.server.config.auth;

import com.nfteam.server.config.auth.dto.OAuthAttributes;
import com.nfteam.server.config.auth.dto.SessionMember;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        OAuth2UserService delegate=new DefaultOAuth2UserService();// dk
        OAuth2User oAuth2User= delegate.loadUser(userRequest); //유저리퀘스트로 받은

        //registrationid
        /*
         * 현재 로그인 징행 중인 서비스 구현하는 코드
         * 이후에 여러가지 추가할 때 네이버읹지 구굴인지 구분해주는 코드
         *  */
        String registrationId=userRequest.getClientRegistration().getRegistrationId();
        // userNameAttributes
        /*
         * Oauth2 로그인 진행 시 키가 되는 필드 값(PK)
         * 구글 기본 코드: sub, 네이버 카카오 등은 기본적으로 지원되지 않음
         * 네이버, 구글 로그인 동시 지원시 사용
         * */

        String userNameAttributes=userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // Oauth2request에서 PK 불러오는 과정

        OAuthAttributes attributes= OAuthAttributes.
                of(registrationId, userNameAttributes, oAuth2User.getAttributes());

        Member member=saveOrUpdate(attributes);

        /*
         * SessionMember
         * 세션에 사용자 정보를 저장하기 위한 dto 클래스
         */
        httpSession.setAttribute("member",new SessionMember(member));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());

    }

    //멤버가 들어왔을 때, 멤버가 기존에 없는 멤버면 이메일로 조회를 통해서 기존에 없는 멤버인지 확인 후 없으면 저장, 있으면 멤버 정보 수정으로 전홙
    private Member saveOrUpdate(OAuthAttributes attributes){
        Member member=memberRepository.findByEmail(attributes.getEmail())
                .map(entity->entity.update(attributes.getNickname(), attributes.getProfileUrl())).orElse(attributes.toEntity());
        return memberRepository.save(member);
    }

}
