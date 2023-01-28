package com.nfteam.server.domain.item.controller;

import com.nfteam.server.domain.item.service.SearchService;
import com.nfteam.server.dto.response.search.SearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<SearchResponse> search(@RequestParam("keyword") String keyword,
                                                 Pageable pageable) {
        return new ResponseEntity<>(searchService.search(keyword, pageable), HttpStatus.OK);
    }

}