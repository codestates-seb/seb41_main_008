package com.nfteam.server.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.response.auth.LoginResponse;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Security - Authentication success");
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        Member member = memberRepository.findByEmail(memberDetails.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(memberDetails.getEmail()));
        member.updateLastLoginTime();
        sendSuccessResponse(response, memberDetails);
    }

    private void sendSuccessResponse(HttpServletResponse response, MemberDetails memberDetails) throws IOException {
        LoginResponse loginResponse = LoginResponse.builder()
                .memberId(memberDetails.getMemberId())
                .email(memberDetails.getEmail())
                .role(memberDetails.getRole())
                .lastLoginTime(memberDetails.getLastLoginTime())
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(OK.value());

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
    }
}
