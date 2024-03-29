package com.nfteam.server.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfteam.server.domain.cart.service.CartService;
import com.nfteam.server.dto.response.auth.LoginResponse;
import com.nfteam.server.dto.response.cart.CartResponse;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final CartService cartService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        sendSuccessResponse(response, memberDetails);
    }

    private void sendSuccessResponse(HttpServletResponse response, MemberDetails memberDetails) throws IOException {
        LoginResponse loginResponse = LoginResponse.builder()
                .id(memberDetails.getMemberId())
                .email(memberDetails.getEmail())
                .role(memberDetails.getRole())
                .lastLoginTime(memberDetails.getLastLoginTime())
                .profileImageName(memberDetails.getProfileImageName())
                .build();

        CartResponse cart = cartService.loadOwnCart(memberDetails.getEmail());
        loginResponse.addCart(cart);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(OK.value());

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
    }

}