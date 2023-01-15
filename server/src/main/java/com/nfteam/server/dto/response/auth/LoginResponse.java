package com.nfteam.server.dto.response.auth;

import com.nfteam.server.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class LoginResponse {

    private String email;
    private String role;
    private String lastLoginTime;

    @Builder
    public LoginResponse(String email, String role, LocalDateTime lastLoginTime) {
        this.email = email;
        this.role = role;
        this.lastLoginTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(lastLoginTime);
    }

    public static LoginResponse of(Member member) {
        return LoginResponse.builder()
                .email(member.getEmail())
                .role(member.getMemberRole().getValue())
                .lastLoginTime(member.getLastLoginTime())
                .build();
    }
}
