package com.nfteam.server.dto.request.auth;

import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.entity.MemberPlatform;
import lombok.Getter;

@Getter
public class OAuthMemberProfile {
    private String email;
    private String nickname;
    private MemberPlatform memberPlatform;

    public OAuthMemberProfile(String email, String nickname, MemberPlatform memberPlatform) {
        this.email = email;
        this.nickname = nickname;
        this.memberPlatform = memberPlatform;
    }

    public Member toMember() {
        return new Member(this.email, this.nickname, this.memberPlatform);
    }
}
