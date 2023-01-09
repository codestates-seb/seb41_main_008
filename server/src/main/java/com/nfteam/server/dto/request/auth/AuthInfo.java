package com.nfteam.server.dto.request.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthInfo {
    private Long userId;
    private String email;
    private String displayName;
    private String role;

    @Builder
    public AuthInfo(final Long userId,
                    final String email,
                    final String displayName,
                    final String role) {
        this.userId = userId;
        this.email = email;
        this.displayName = displayName;
        this.role = role;
    }
}