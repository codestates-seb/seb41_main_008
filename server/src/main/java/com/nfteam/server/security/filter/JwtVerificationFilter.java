package com.nfteam.server.security.filter;

import com.nfteam.server.common.utils.JwtTokenizer;
import com.nfteam.server.exception.token.TokenNotValidateException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
            setAuthToContextHolder(accessToken);
        } catch (SignatureException e) {
            throw new TokenNotValidateException("JWT 시그니처 정보가 잘못되었습니다.");
        } catch (ExpiredJwtException e) {
            throw new TokenNotValidateException("JWT 유효기한이 만료되었습니다.");
        } catch (Exception e) {
            throw new TokenNotValidateException("JWT 토큰을 검증하는 데 실패하였습니다.");
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthToContextHolder(String accessToken) {
        Authentication authentication = jwtTokenizer.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return headerNotValidate(request) || isLoginLogoutRequest(request) || isSignUpRequest(request) || isRefreshRequest(request);
    }

    private boolean headerNotValidate(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return header == null || !header.startsWith("Bearer");
    }

    private boolean isLoginLogoutRequest(HttpServletRequest request) {
        boolean isLoginRequest = request.getRequestURI().contains("/auth/login");
        boolean isLogoutRequest = request.getRequestURI().contains("/auth/logout");
        return isLoginRequest || isLogoutRequest;
    }

    private boolean isSignUpRequest(HttpServletRequest request) {
        return request.getRequestURI().contains("/api/members") &&
                request.getMethod().equalsIgnoreCase("POST");
    }

    private boolean isRefreshRequest(HttpServletRequest request) {
        return request.getRequestURI().contains("/auth/reissue");
    }

}