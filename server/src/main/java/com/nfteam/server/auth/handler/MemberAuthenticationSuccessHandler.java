package com.nfteam.server.auth.handler;

import static org.springframework.http.HttpStatus.OK;

import com.google.gson.Gson;
import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.response.auth.LoginResult;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Authentication 객체에 사용자 정보를 얻은 후,
        // HttpServletResponse로 출력 스트림을 생성하여 response를 전송할 수 있다
        log.info("Authenticated successfully!");
        sendSuccessResponse(response, authentication);
    }

    private void sendSuccessResponse(HttpServletResponse response, Authentication authentication) throws IOException {
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        String memberEmail = memberDetails.getEmail();
        Member findMember = memberRepository.findByEmail(memberEmail).orElseThrow(() -> new MemberNotFoundException(memberEmail));
        findMember.setLastLogin(LocalDateTime.now());
        memberRepository.save(findMember);

        // 로그인 결과를 보여주는 별도의 dto 생성 후 리턴
        LoginResult loginResult = LoginResult.builder()
                .memberId(memberDetails.getMemberId())
                .email(memberEmail)
                .roles(memberDetails.getRoles())
                .lastLogin(memberDetails.getLastLogin()).build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(OK.value());

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(loginResult, LoginResult.class));
    }
}
