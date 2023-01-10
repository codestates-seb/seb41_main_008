package com.nfteam.server.config.auth.dto;

import com.nfteam.server.member.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable { //직렬화용 dto
    private String nickname;
    private String email;

    private String profileUrl;

    public SessionMember(Member member){
        this.nickname=member.getNickname();
        this.email=member.getEmail();
        this.profileUrl=member.getProfileUrl();
    }

}
