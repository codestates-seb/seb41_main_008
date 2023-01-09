package com.nfteam.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerTestController {
    @GetMapping
    public String homeTest() {
<<<<<<< HEAD
        return "서버 정상 동작 확인 프론트용";
=======
        return "운영서버 무중단 배포 확인 2";
>>>>>>> 933c1d1dc3398940aa0f28b638e51791bf3829fe
    }
}
