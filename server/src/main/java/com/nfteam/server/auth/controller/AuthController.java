package com.nfteam.server.auth.controller;

import com.nfteam.server.auth.service.AuthService;
import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.exception.token.RefreshTokenExpiredException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/logout")
    public ResponseEntity logoutMember(
        HttpServletRequest request,@AuthenticationPrincipal MemberDetails memberDetails){
        authService.logout(request,memberDetails.getMemberId());

        return new ResponseEntity("로그아웃 되었습니다.", HttpStatus.OK);

    }
    @GetMapping("/reissuance")
    public ResponseEntity reissue(HttpServletRequest request,@AuthenticationPrincipal MemberDetails memberDetails){
        String refreshToken = request.getHeader("RefreshToken");
        String reissuedToken = authService.reissue(memberDetails,refreshToken);

        return new ResponseEntity(reissuedToken,HttpStatus.CREATED);
    }
}
