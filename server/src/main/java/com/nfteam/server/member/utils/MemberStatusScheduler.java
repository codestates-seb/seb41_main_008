package com.nfteam.server.member.utils;


import com.nfteam.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MemberStatusScheduler {
    private final MemberService memberService;

    // 매일 새벽 1시 10분 : 마지막 로그인 시간이 6개월을 넘은 회원은 휴면 회원 처리
    @Scheduled(cron = "0 10 1 * * ?")
    public void updateMemberStatus() {
        memberService.updateMemberStatus();
        log.info("member-status updated successfully!");
    }

}