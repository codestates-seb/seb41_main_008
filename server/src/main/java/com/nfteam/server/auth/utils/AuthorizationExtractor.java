package com.nfteam.server.auth.utils;

import com.nfteam.server.exception.token.ExtractTokenFailedException;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class AuthorizationExtractor {

    private static final String PRE_FIX = "Bearer";

    private AuthorizationExtractor() {
    }

    public static String getAccessToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(HttpHeaders.AUTHORIZATION);
        return extract(headers);
    }

    public static String extract(final Enumeration<String> headers) {
        while (headers.hasMoreElements()) {
            String headerValue = headers.nextElement();
            if ((headerValue.toLowerCase().startsWith(PRE_FIX.toLowerCase()))) {
                String authHeader = headerValue.substring(PRE_FIX.length()).trim();
                int commaPos = authHeader.indexOf(',');
                if (commaPos > 0) {
                    authHeader = authHeader.substring(0, commaPos);
                }
                return authHeader;
            }
        }
        throw new ExtractTokenFailedException();
    }


}
