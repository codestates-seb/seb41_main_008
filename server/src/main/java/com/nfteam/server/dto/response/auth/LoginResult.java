package com.nfteam.server.dto.response.auth;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class LoginResult {
    private String email;
    private long memberId;
    private List<String> roles;
    private String lastLogin;

    @Builder
    public LoginResult(String email, long memberId, List<String> roles, LocalDateTime lastLogin) {
        this.email = email;
        this.memberId = memberId;
        this.roles = roles;
        this.lastLogin = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(lastLogin);
    }
}
