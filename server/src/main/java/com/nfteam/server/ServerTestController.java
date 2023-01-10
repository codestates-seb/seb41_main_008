package com.nfteam.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerTestController {
    @GetMapping
    public String homeTest() {
<<<<<<< HEAD

        return "운영서버 무중단 배포 확인 2";
   }
=======
        return "서버 정상 동작 확인 프론트용";
    }
>>>>>>> 55de21a8bd86aa6c16cece92d5ab351acd6cc92e
}
