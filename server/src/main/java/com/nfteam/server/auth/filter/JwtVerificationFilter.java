package com.nfteam.server.auth.filter;

import com.nfteam.server.auth.jwt.JwtTokenizer;
import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.auth.utils.CustomAuthorityUtils;
import com.nfteam.server.member.entity.Member;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;

    private final CustomAuthorityUtils authorityUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 검증 실패했을 때 생기는 SignatureException 과
        // JWT가 만료될 경우에 생기는 ExpiredJwtException에대한 처리

        try {
            Map<String, Object> claims = verifyJws(request);
            // Authentication 객체를 securityContext에 저장
            setAuthenticationToContext(claims);
            // 서명 검증에 실패했을 때 SecurityContext에
            // 인증정보인 Authentication 객체가 저장되지 않는다.
        } catch (SignatureException se) {
            se.printStackTrace();
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            ee.printStackTrace();
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //jwt가 Authorization header에 포함되지 않았다면 filter 실행하지 않는다.
        String authorization = request.getHeader("Authorization");
        return authorization == null || !authorization.startsWith("Bearer");
    }

    /**
     * 도구
     */
    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        String jws = accessToken.replace("Bearer ", "");
        return jwtTokenizer.getClaims(jws).getBody();
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        /* JWT Token 내부의 값(claims)을 이용해 MemberDetails 생성
         -> Security Context에 MemberDetails 객체 저장
         -> 컨트롤러에서 @AuthenticatonPrincipal MemberDetails 로 조회 가능*/

        Long memberId = Long.parseLong(String.valueOf(claims.get("memberId")));
        String username = (String) claims.get("username");
        List<String> roles = (List<String>) claims.get("roles");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(roles);

        Member member = new Member();
        member.setMemberId(memberId);
        member.setEmail(username);
        member.setRoles(roles);

        MemberDetails memberDetails = new MemberDetails(authorityUtils, member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
