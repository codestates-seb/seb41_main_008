package com.nfteam.server.config;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
//          .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
//          .accessDeniedHandler(new MemberAccessDeniedHandler())
            .and()
//          .apply(new CustomFilterConfigurer())
//          .and()
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll());
//            .oauth2Login(oauth2 -> oauth2
//                .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, authorityUtils, memberRepository, oauthMemberRepository)));

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


}
