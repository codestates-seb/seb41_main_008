package com.nfteam.server.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServerProfileCheckController {
    private static final List<String> PROFILES = Arrays.asList("deploy1", "deploy2");
    private static final String DEFAULT = "default";
    private final Environment env;

    @GetMapping("/profile")
    public String getProfile() {
        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        String defaultProfile = activeProfiles.isEmpty() ? DEFAULT : activeProfiles.get(0);

        return activeProfiles.stream()
                .filter(PROFILES::contains)
                .findAny()
                .orElse(defaultProfile);
    }

    @GetMapping
    public String homeTest() {
        return "개발 서버 정상 동작 확인 테스트 - Oauth 리다이렉트 변경 변경사항 반영";
    }
}
