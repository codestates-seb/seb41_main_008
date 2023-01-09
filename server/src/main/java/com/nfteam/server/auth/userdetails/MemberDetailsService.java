package com.nfteam.server.auth.userdetails;

import com.nfteam.server.auth.utils.CustomAuthorityUtils;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final CustomAuthorityUtils customAuthorityUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByEmail(username).orElseThrow(
                () -> new MemberNotFoundException(username));

        return new MemberDetails(customAuthorityUtils, findMember);
    }
}