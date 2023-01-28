package com.nfteam.server.security.userdetails;

import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.exception.member.MemberStatusNotActiveException;
import com.nfteam.server.domain.member.entity.Member;
import com.nfteam.server.domain.member.entity.MemberStatus;
import com.nfteam.server.domain.member.repository.MemberRepository;
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
        checkMemberStatus(findMember.getMemberStatus());
        findMember.updateLastLoginTime();
        return new MemberDetails(findMember);
    }

    private void checkMemberStatus(MemberStatus memberStatus) {

        if (memberStatus.equals(MemberStatus.MEMBER_QUIT)) {
            throw new MemberStatusNotActiveException();
        }
    }

}