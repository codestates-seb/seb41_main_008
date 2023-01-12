package com.nfteam.server.dto.response.auth;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class LoginResponse {
    private Long memberId;
    private String email;
    private String role;
    private String lastLoginTime;

    @Builder
    public LoginResponse(Long memberId, String email, String role, LocalDateTime lastLoginTime) {
        this.memberId = memberId;
        this.email = email;
        this.role = role;
        this.lastLoginTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(lastLoginTime);
    }
}
