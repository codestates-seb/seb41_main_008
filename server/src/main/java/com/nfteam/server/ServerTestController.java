package com.nfteam.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerTestController {
    @GetMapping
    public String homeTest() {
        return "서버 정상 동작 확인 백엔드 개발용 2";
    }
}
