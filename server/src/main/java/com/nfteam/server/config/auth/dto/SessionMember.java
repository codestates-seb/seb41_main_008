package com.nfteam.server.config.auth.dto;

import com.nfteam.server.member.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable { //직렬화용 dto
    private final String nickname;
    private final String email;

    private final String profileUrl;

    public SessionMember(Member member){
        this.nickname=member.getNickname();
        this.email=member.getEmail();
        this.profileUrl=member.getProfileImageName();
    }

}
