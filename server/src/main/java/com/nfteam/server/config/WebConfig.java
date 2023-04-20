package com.nfteam.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final List<HandlerInterceptor> interceptors;
    private final List<HandlerMethodArgumentResolver> argumentResolvers;

    public WebConfig(List<HandlerInterceptor> interceptors, List<HandlerMethodArgumentResolver> argumentResolvers) {
        this.interceptors = interceptors;
        this.argumentResolvers = argumentResolvers;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptors.forEach(registry::addInterceptor);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.addAll(argumentResolvers);
    }

}