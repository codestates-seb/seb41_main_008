package com.nfteam.server.config.auth;

import com.nfteam.server.config.auth.dto.MemberProfile;
import com.nfteam.server.config.auth.dto.OAuthAttributes;
//import com.nfteam.server.config.auth.dto.SessionMember;
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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        OAuth2UserService delegate=new DefaultOAuth2UserService();
        OAuth2User oAuth2User= delegate.loadUser(userRequest);
        //각 Oauth 서비스 사에서 받은 유저 정보를 담고 있음

        //registrationid
        /*
         * 현재 로그인 징행 중인 서비스 구현하는 코드
         * 이후에 여러가지 추가할 때 네이버읹지 구굴인지 구분
         *  */
        String registrationId=userRequest.getClientRegistration().getRegistrationId();
        // userNameAttributes
        /*
         * Oauth2 로그인 진행 시 키가 되는 필드 값(PK)
         * 구글 기본 코드: sub, 네이버 카카오 등은 기본적으로 지원되지 않음
         * 네이버, 구글 로그인 동시 지원시 사용
         * */

        String userNameAttributes=userRequest.getClientRegistration()
                                             .getProviderDetails()
                                             .getUserInfoEndpoint()
                                              .getUserNameAttributeName(); // Oauth2request에서 PK 불러오는 과정

        Map<String, Object> attributes=oAuth2User.getAttributes(); //Oauth 서비스의 유저 정보들

        MemberProfile memberProfile=OAuthAttributes.extract(registrationId,attributes); //registrationId에 따라, 유저 정보를 통해 공통된 User 객체를 만들어줌

        memberProfile.setProvider(registrationId); // 리소스 Provider 정보를 memberProfile에 저장

        Member member=saveOrUpdate(memberProfile);


        Map<String, Object> customAttribute=customAttribute(attributes,userNameAttributes,memberProfile,registrationId);





        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                customAttribute,
                userNameAttributes);

    }

    private Map<String, Object> customAttribute(Map<String, Object> attributes,
                                                String userNameAttributes,
                                                MemberProfile memberProfile,
                                                String registrationId) {

        Map<String, Object> customAttribute=new LinkedHashMap<>();
        customAttribute.put(userNameAttributes,attributes.get(userNameAttributes));
        customAttribute.put("provider",registrationId);
        customAttribute.put("nickname",memberProfile.getNickname());
        customAttribute.put("email",memberProfile.getEmail());
        return attributes;
    }

    //이미 생성된 사용자인지 아닌지 처음 가입하는 사용자인지 이메일, ResourceProvider를 통해 구분
    private Member saveOrUpdate(MemberProfile memberProfile){
        Member member=memberRepository.findByEmailAndProvider(memberProfile.getEmail(),memberProfile.getResourceProvider())
                .map(m->m.update(memberProfile.getEmail(),memberProfile.getNickname(),memberProfile.getResourceProvider())) //Oauth 정보에서 변경된 이메일이나 닉네임이 있으면 수정
                .orElse(memberProfile.toMember());  //테이블에 일치하는 멤버가 없다면 수정
        return memberRepository.save(member);

    }

}
