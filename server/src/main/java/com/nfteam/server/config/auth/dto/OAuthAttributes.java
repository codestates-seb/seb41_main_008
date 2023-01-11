package com.nfteam.server.config.auth.dto;

import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.entity.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;



import java.util.Map;

@Builder(access= AccessLevel.PRIVATE)
@Getter //Oauth 인증시 필요한 데이터
public class OAuthAttributes {
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String nickname;
    private String email;
    private String profileUrl;


    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                           String nickname, String email, String profileUrl){
        this.attributes=attributes;
        this.nameAttributeKey=nameAttributeKey;
        this.nickname=nickname;
        this.email=email;
        this.profileUrl=profileUrl;
    }

    /*
     *OAuth 에 반환하는 사용자 정보는 Map 형태이므로 값 하나하나를 반환해줘야 함
     */

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profileUrl((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /*
     * toEntity()
     * member 엔티티 생성
     * OauthAttributes에서 엔티티 생성 시점은 처음 가입 시이다.
     * OauthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionMember 클래스를 생성해줌
     * */

    public Member toEntity(){
        return Member.
                builder()
                .nickname(nickname)
                .email(email)
                .profileUrl(profileUrl)
                .role(Role.GUEST) //가장 기본 권한
                .build();
    }
}
