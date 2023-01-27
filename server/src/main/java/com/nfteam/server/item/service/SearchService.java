package com.nfteam.server.item.service;

import com.nfteam.server.dto.response.common.PageResponse;
import com.nfteam.server.dto.response.search.SearchCollectionResponse;
import com.nfteam.server.dto.response.search.SearchItemResponse;
import com.nfteam.server.dto.response.search.SearchResponse;
import com.nfteam.server.item.repository.QSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SearchService {

    private final QSearchRepository qSearchRepository;

    public SearchService(QSearchRepository qSearchRepository) {
        this.qSearchRepository = qSearchRepository;
    }

    @Transactional
    public SearchResponse search(String keyword, int page, int size) {
        Page<SearchItemResponse> items = qSearchRepository.searchItemWithKeyword(keyword,
                PageRequest.of(page, size, Sort.by("createdDate").descending()));

        List<SearchCollectionResponse> collections = qSearchRepository.searchCollectionWithKeyword(keyword);

        return new SearchResponse(collections, new PageResponse<>(items.getContent(), items));
    }

}