package com.nfteam.server.config.auth.dto;


import com.nfteam.server.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberProfile {
    private String email;
    private String resourceProvider;
    private String nickname;

    public Member toMember(){
        return Member.builder()
                .email(email)
                .provider(resourceProvider)
                .nickname(nickname)
                .build();
    }

    public void setProvider(String registrationId) {
        this.resourceProvider=registrationId;
    }
}
