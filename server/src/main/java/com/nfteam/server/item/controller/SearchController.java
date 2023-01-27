package com.nfteam.server.item.controller;

import com.nfteam.server.dto.response.search.SearchResponse;
import com.nfteam.server.item.service.SearchService;
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
                                                 @RequestParam("page") int page,
                                                 @RequestParam("size") int size) {
        return new ResponseEntity<>(searchService.search(keyword, page - 1, size), HttpStatus.OK);
    }

}