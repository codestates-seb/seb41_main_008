package com.nfteam.server.support;

import com.nfteam.server.exception.support.UrlLengthInvalidException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UriCheckInterceptor implements HandlerInterceptor {

    private static final String DELIMITER = "?";
    private static final int MAX_LENGTH = 800;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (getFullUrl(request).length() > MAX_LENGTH) {
            throw new UrlLengthInvalidException();
        } else return true;
    }

    private String getFullUrl(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();
        if (queryString == null) {
            return requestUri;
        } else return requestUri + DELIMITER + queryString;
    }

}