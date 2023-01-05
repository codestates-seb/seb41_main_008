package com.nfteam.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String ALLOW_METHODS = "GET,POST,PATCH,PUT,DELETE,HEAD,OPTIONS";
    private static final String LOCALHOST_FRONT = "http://localhost:3000";
    private static final String LOCALHOST_BACK = "http://localhost:8080";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(LOCALHOST_FRONT, LOCALHOST_BACK)
                .allowedMethods(ALLOW_METHODS.split(","))
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.LOCATION);
    }
}


