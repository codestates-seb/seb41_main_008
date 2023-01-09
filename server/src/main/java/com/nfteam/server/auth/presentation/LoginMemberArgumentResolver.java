package com.nfteam.server.auth.presentation;

import com.nfteam.server.auth.jwt.JwtTokenizer;
import com.nfteam.server.auth.utils.AuthorizationExtractor;
import com.nfteam.server.dto.request.auth.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenizer jwtTokenizer;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String accessToken = AuthorizationExtractor.getAccessToken(Objects.requireNonNull(request));
        if (accessToken == null) {
            return new AuthInfo(null, null, null, null);
        }

        return null;


    }
}
