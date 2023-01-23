package com.nfteam.server.ranking.controller;

import com.nfteam.server.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ranking")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/time/{time}")
    public ResponseEntity getTimeRanking(@PathVariable("time") String time) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}