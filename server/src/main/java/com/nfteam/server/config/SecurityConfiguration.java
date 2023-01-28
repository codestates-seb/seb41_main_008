package com.nfteam.server.config;

import com.nfteam.server.auth.utils.JwtTokenizer;
import com.nfteam.server.domain.cart.service.CartService;
import com.nfteam.server.redis.repository.RedisRepository;
import com.nfteam.server.security.filter.JwtAuthenticationFilter;
import com.nfteam.server.security.filter.JwtExceptionFilter;
import com.nfteam.server.security.filter.JwtVerificationFilter;
import com.nfteam.server.security.handler.MemberAccessDeniedHandler;
import com.nfteam.server.security.handler.MemberAuthenticationEntryPoint;
import com.nfteam.server.security.handler.MemberAuthenticationFailureHandler;
import com.nfteam.server.security.handler.MemberAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
public class SecurityConfiguration {

    private static final String HTTPS_SERVER_DOMAIN = "https://www.nfteam008.com";
    private static final String LOCALHOST = "http://localhost:3000";

    private final JwtTokenizer jwtTokenizer;
    private final RedisRepository redisRepository;
    private final CartService cartService;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, RedisRepository redisRepository, CartService cartService) {
        this.jwtTokenizer = jwtTokenizer;
        this.redisRepository = redisRepository;
        this.cartService = cartService;
    }

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
                        .antMatchers(HttpMethod.GET, "/api/members/mypage", "/api/coins/my").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/api/collections", "/api/items", "/api/items/sell/**", "/api/carts/save", "/api/trans", "/images", "/api/coins/purchase").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/api/members/**", "/api/items/**", "/api/collections/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/api/members/**", "/api/collections/**", "/api/items/**").hasRole("USER")
                        .anyRequest().permitAll()
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(HTTPS_SERVER_DOMAIN, LOCALHOST));
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
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(cartService));
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