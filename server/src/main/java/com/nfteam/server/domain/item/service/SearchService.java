package com.nfteam.server.domain.item.service;

import com.nfteam.server.domain.item.repository.QSearchRepository;
import com.nfteam.server.dto.response.common.SliceResponse;
import com.nfteam.server.dto.response.search.SearchCollectionResponse;
import com.nfteam.server.dto.response.search.SearchItemResponse;
import com.nfteam.server.dto.response.search.SearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    public SearchResponse search(String keyword, Pageable pageable) {
        Slice<SearchItemResponse> slice = qSearchRepository.searchItemWithKeyword(keyword, pageable);
        List<SearchCollectionResponse> collections = qSearchRepository.searchCollectionWithKeyword(keyword);

        // 아이템 리스트 슬라이스 전환
        SliceResponse<SearchItemResponse> sliceResponse;
        if (slice.isEmpty()) {
            sliceResponse = new SliceResponse<>(false, slice.getContent());
        } else {
            sliceResponse = new SliceResponse<>(true, slice.getContent());
        }

        return new SearchResponse(collections, sliceResponse);
    }

}