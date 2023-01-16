package com.nfteam.server.common.config;

import com.nfteam.server.common.utils.JwtTokenizer;
import com.nfteam.server.redis.repository.RedisRepository;
import com.nfteam.server.security.filter.JwtAuthenticationFilter;
import com.nfteam.server.security.filter.JwtExceptionFilter;
import com.nfteam.server.security.filter.JwtVerificationFilter;
import com.nfteam.server.security.handler.MemberAccessDeniedHandler;
import com.nfteam.server.security.handler.MemberAuthenticationEntryPoint;
import com.nfteam.server.security.handler.MemberAuthenticationFailureHandler;
import com.nfteam.server.security.handler.MemberAuthenticationSuccessHandler;
import com.nfteam.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;
    private final RedisRepository redisRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        // TODO: 편의상 권한 적용은 개발 마지막 단계에 적용
                        // .antMatchers(HttpMethod.POST, "/api/members").permitAll()
                        // .antMatchers(HttpMethod.GET, "/api/members/**", "/api/items/**").hasRole("USER")
                        // .antMatchers(HttpMethod.POST, "/api/items").hasRole("USER")
                        // .antMatchers(HttpMethod.PATCH, "/api/members/**", "/api/items/**").hasRole("USER")
                        // .antMatchers(HttpMethod.DELETE, "/api/members/**", "/api/items/**").hasRole("USER")
                        .anyRequest().permitAll()
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));
        configuration.setExposedHeaders(List.of("RefreshToken", HttpHeaders.AUTHORIZATION, HttpHeaders.LOCATION));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, redisRepository);
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(memberRepository));
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer);
            JwtExceptionFilter jwtExceptionFilter = new JwtExceptionFilter();

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtExceptionFilter, JwtAuthenticationFilter.class)
                    .addFilterAfter(jwtVerificationFilter, JwtExceptionFilter.class);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}