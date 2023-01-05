package com.nfteam.server.auth.userdetails;

import com.nfteam.server.auth.utils.CustomAuthorityUtils;
import com.nfteam.server.member.entity.Member;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MemberDetails extends Member implements UserDetails {

    private CustomAuthorityUtils authorityUtils;

    public MemberDetails(Member member,CustomAuthorityUtils authorityUtils) {
        this.authorityUtils = authorityUtils;
        setMemberId(member.getMemberId());
        setEmail(member.getEmail());
        setPassword(member.getPassword());
        setRoles(member.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
