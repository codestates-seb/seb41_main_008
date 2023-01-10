package com.nfteam.server.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfteam.server.auth.jwt.JwtTokenizer;
import com.nfteam.server.auth.repository.RedisRepository;
import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.request.auth.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 로그인 시 인증정보 확인(AuthenticationManager가 UserDetailService를 호출하여 UserDetail을 확인) 후
 * 토큰발급(jwtTokenizer)
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenizer jwtTokenizer;

    private final RedisRepository redisRepository;

    /**
     * 입력받은 username, password를 바탕으로 인증정보를 생성하는 로직
     * 1. ObjectMapper로 request에서 loginDto로 username,password를 담는다.
     * 2. 이 정보를 가지고 '인증 전'(권한 설정이 되어 있지 않은) authenticationToken 발급
     * 3. authenticationToken을 이용하여 UserDetailsService에서 인증 정보를 받아온다.
     * -> (내부적으로 해주기에 코드상에 보이진 않음)
     *
     * @Return : 인증이 완료된(권한부여된) Authentication
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //JSON to Java Object
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * attempt 성공시 진입하게 될 메서드.
     * 1. 인증된 Authentication의 field에서 Member정보를 가져온다.
     * 2. 가져온 member를 이용하여 액세스토큰, 리프레시 토큰을 생성한다.
     * 3. 리프레시 토큰을 redis에 저장한다. 4. 액세스,리프레시 토큰을 헤더에 담는다.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws ServletException, IOException {
        MemberDetails member = (MemberDetails) authentication.getPrincipal();
        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member);

        //redis db에 리프레시 토큰 저장
        redisRepository.saveRefreshToken(String.valueOf(member.getMemberId()), refreshToken);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("RefreshToken", refreshToken);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authentication);
    }

    private String delegateRefreshToken(MemberDetails member) {
        return jwtTokenizer.generateRefreshToken(member.getEmail());
    }

    private String delegateAccessToken(MemberDetails member) {
        //claim 생성
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        //subject
        String subject = member.getEmail();

        return jwtTokenizer.generateAccessToken(claims, subject);
    }

}
