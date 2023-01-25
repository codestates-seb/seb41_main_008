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

    @Scheduled(cron = "0 0 4 ? * *")
    public void updateMemberStatus(){
        memberService.updateMemberStatus();
        log.info("member-status updated successfully!");
    }

}
