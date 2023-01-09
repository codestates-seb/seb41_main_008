package com.nfteam.server.config;

import com.nfteam.server.auth.filter.JwtAuthenticationFilter;
import com.nfteam.server.auth.filter.JwtVerificationFilter;
import com.nfteam.server.auth.handler.MemberAccessDeniedHandler;
import com.nfteam.server.auth.handler.MemberAuthenticationEntryPoint;
import com.nfteam.server.auth.handler.MemberAuthenticationFailureHandler;
import com.nfteam.server.auth.handler.MemberAuthenticationSuccessHandler;
import com.nfteam.server.auth.jwt.JwtTokenizer;
import com.nfteam.server.auth.repository.RedisRepository;
import com.nfteam.server.auth.utils.CustomAuthorityUtils;
import com.nfteam.server.member.repository.MemberRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenizer jwtTokenizer;

    private final CustomAuthorityUtils authorityUtils;

    private final MemberRepository memberRepository;

    private final RedisRepository redisRepository;

    @Bean
    public SecurityFilterChain secutiryFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .headers().frameOptions().sameOrigin()
            .and()
            .csrf().disable() //csrf security 비활성화
            .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(
                corsConfigurationSource()))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .exceptionHandling()
            .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
            .accessDeniedHandler(new MemberAccessDeniedHandler())
            .and()
            .apply(new CustomFilterConfigurer())
            .and()
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, authorityUtils, memberRepository, oauthMemberRepository)))
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll());


        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity>{

        @Override
        public void configure(HttpSecurity builder) throws Exception {

            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager,jwtTokenizer,redisRepository);
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(memberRepository)); //success 핸들러
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler()); //failure 핸들러
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/members/login");

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder.addFilter(jwtAuthenticationFilter)
                .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);


        }

    }
}
