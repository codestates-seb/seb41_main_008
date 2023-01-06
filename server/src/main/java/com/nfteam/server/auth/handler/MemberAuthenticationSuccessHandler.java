package com.nfteam.server.auth.handler;

import static org.springframework.http.HttpStatus.OK;

import com.google.gson.Gson;
import com.nfteam.server.auth.userdetails.MemberDetails;
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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        // Authentication 객체에 사용자 정보를 얻은 후, HttpServletResponse로
        // 출력 스트림을 생성하여 response를 전송할 수 있다
        log.info("# Authenticated successfully!");
        sendSuccessResponse(response,authentication);
    }

    private void sendSuccessResponse(HttpServletResponse response,Authentication authentication)
        throws IOException {
        Gson gson = new Gson();
        MemberDetails memberDeatils = (MemberDetails) authentication.getPrincipal();
        memberDeatils.setLastLogin(LocalDateTime.now());

        Optional<Member> findmember = memberRepository.findByEmail(memberDeatils.getEmail());
        findmember.get().setLastLogin(LocalDateTime.now());
        memberRepository.save(findmember.get());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(OK.value());
        response.getWriter().write(gson.toJson(memberDeatils,memberDeatils.getClass()));
    }
}
