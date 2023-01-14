package com.nfteam.server.security.userdetails;

import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * @param username username == email
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberNotFoundException(username));
        findMember.updateLastLoginTime();
        return new MemberDetails(findMember);
    }
}
