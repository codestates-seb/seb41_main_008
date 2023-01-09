package com.nfteam.server.config.auth;

import com.nfteam.server.member.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOauth2UserService customOauth2UserService;


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf().disable()
                .headers().frameOptions().disable() // h2 콘솔을 위한 설정들 off
                .and()
                .authorizeRequests() //URL 별 권한 권리를 설정하는 옵션의 시작점이라고 생각하면 됩니다.
                .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**","/profile").permitAll()
                //anyMatcher-> 권한 관리 대상 지정, URL HTTP 메소드 별 관리를 가능하게 해줌
                .antMatchers("/api/members/**").hasRole(Role.USER.name())  //"/api/members/**" 주소를 가진 api USER 권한을 가진 사람만 가능
                .anyRequest().authenticated()//설정된 값들 이외의 나머지 URL들도 모두 사용자들에게만 허용하게 설정
                .and()
                .logout().logoutSuccessUrl("/") //로그아웃 성공 시 / 주소로 이동
                .and()
                .oauth2Login() //OAUTH 로그인 성공 이후 사용자 정보를 가져올 때의 설정들 담당
                .userInfoEndpoint()
                .userService(customOauth2UserService); //로그인 진행 후 Oauth2Login에서 제공하는 userService를 이용해 리소스 서버(구글, 카카오, 네이버 등)에서 사용자 정보를 가져온 상태엑서 추가로 진행하고자 하는 명시 가능
    }
}
