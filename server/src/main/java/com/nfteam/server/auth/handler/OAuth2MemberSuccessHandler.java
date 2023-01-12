package com.nfteam.server.auth.handler;

import com.nfteam.server.auth.jwt.JwtTokenizer;
import com.nfteam.server.auth.repository.RedisRepository;
import com.nfteam.server.auth.utils.CustomAuthorityUtils;

import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberRepository memberRepository;


    private final RedisRepository redisRepository;



    //인증 성공 시 핸들러
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                         final HttpServletResponse response,
                                         final Authentication authentication) throws IOException {
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();


        String email=String.valueOf(oAuth2User.getAttributes().get("email"));
        Member googleMember=createOrUpdateGoogleMember(email);



        redirect(request,response,email, googleMember);
    }

    //로그인 후 리다이렉트
    private void redirect(HttpServletRequest request,
                          HttpServletResponse response,
                          String membername,
                          Member googleMember) throws IOException {

        String accessToken = delegateAccessToken(googleMember);
        String refreshToken = delegateRefreshToken(membername);

        response.setHeader("Authorization", "Bearer"+accessToken);
        response.setHeader("Refresh",refreshToken);


        String uri=createURI().toString();
        getRedirectStrategy().sendRedirect(request,response,uri);


    }

    private URI createURI() {
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("ec2-3-35-204-189.ap-northeast-2.compute.amazonaws.com")
                .path("/")
                .build()
                .toUri();
    }

    private String delegateRefreshToken(final String membername) {
        return jwtTokenizer.generateRefreshToken(membername);
    }

    //jwt token을 통해서 accessToken 발급
    private String delegateAccessToken(Member googleMember) {
        //Oauth는 Map 형태로 통신하므로 map 형태로 변환
        Map<String, Object> claims=new HashMap<>();

        claims.put("memberId",googleMember.getMemberId());
        claims.put("username", googleMember.getEmail() );
        claims.put("role","USER");

        return jwtTokenizer.generateAccessToken(claims,googleMember.getEmail());
    }

    private Member createOrUpdateGoogleMember(final String email) {
        Member member;

        if(isFirstLogin(email)){
            member=Member.transToGoogle(email);
            //String encryptedPassword=passwordEncoder.encode(UUID.randomUUID().toString());
            //member.changePassword(encryptedPassword); // 위에서 암호화한 비밀번호로 변경

            member.changeProfile(member.getProfileImageName());
            memberRepository.save(member);

        } else { //기존에 없는 회원이라면 테이블 새로운 회원으로 추가
            member=memberRepository.findByEmail(email).get();
        }

        return member;
    }

    private boolean isFirstLogin(String email) {
        return !memberRepository.existsByEmail(email);
    }




}
