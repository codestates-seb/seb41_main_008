package com.nfteam.server.security.userdetails;

import com.nfteam.server.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
public class MemberDetails extends Member implements UserDetails {

    private Long memberId;
    private String email;
    private String password;
    private String nickname;
    private String role;
    private LocalDateTime lastLoginTime;
    private String profileImage;

    public MemberDetails(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.role = member.getMemberRole().getValue();
        this.lastLoginTime = member.getLastLoginTime();
        this.profileImage = member.getProfileImage();
    }

    public MemberDetails(Long memberId, String email, String nickname, String role) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
