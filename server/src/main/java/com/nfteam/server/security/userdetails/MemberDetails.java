package com.nfteam.server.security.userdetails;

import com.nfteam.server.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
public class MemberDetails extends Member implements UserDetails {

    private final List<GrantedAuthority> GUEST_ROLES = AuthorityUtils.createAuthorityList("ROLE_GUEST");
    private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
    private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN");

    private Long memberId;
    private String email;
    private String password;
    private String nickname;
    private String role;
    private LocalDateTime lastLoginTime;
    private String profileImageName;

    public MemberDetails(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.role = member.getMemberRole().getValue();
        this.lastLoginTime = member.getLastLoginTime();
        this.profileImageName = member.getProfileImageName();
    }

    public MemberDetails(String memberId, String email, String nickname, String role) {
        this.memberId = Long.parseLong(memberId);
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return createAuthorities(this.role);
    }

    private Collection<? extends GrantedAuthority> createAuthorities(String role) {
        if (role.equals("USER")) return USER_ROLES;
        else if (role.equals("ADMIN")) return ADMIN_ROLES;
        else return GUEST_ROLES;
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