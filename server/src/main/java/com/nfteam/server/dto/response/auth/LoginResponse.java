package com.nfteam.server.dto.response.auth;

import com.nfteam.server.domain.member.entity.Member;
import com.nfteam.server.dto.response.cart.CartResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class LoginResponse {

    private Long id;
    private String email;
    private String role;
    private String lastLoginTime;
    private String profileImageName;
    private Long cartId;

    @Builder
    public LoginResponse(Long id,
                         String email,
                         String role,
                         LocalDateTime lastLoginTime,
                         String profileImageName) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.lastLoginTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(lastLoginTime);
        this.profileImageName = profileImageName;
    }

    public static LoginResponse of(Member member) {
        return LoginResponse.builder()
                .id(member.getMemberId())
                .email(member.getEmail())
                .role(member.getMemberRole().getValue())
                .lastLoginTime(member.getLastLoginTime())
                .profileImageName(member.getProfileImageName())
                .build();
    }

    public void addCart(CartResponse cart) {
        this.cartId = cart.getCartId();
    }

}