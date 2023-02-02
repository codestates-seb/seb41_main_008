package com.nfteam.server.domain.ranking.controller;

import com.nfteam.server.domain.ranking.service.RankingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/time/{time}")
    public ResponseEntity getTimeRanking(@PathVariable("time") String time) {
        return new ResponseEntity<>(rankingService.getTimeRanking(time), HttpStatus.OK);
    }

    @GetMapping("/coin/{coinId}")
    public ResponseEntity getCoinRanking(@PathVariable("coinId") Long coinId) {
        return new ResponseEntity<>(rankingService.getCoinRanking(coinId), HttpStatus.OK);
    }

}