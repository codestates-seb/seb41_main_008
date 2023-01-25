package com.nfteam.server.dto.response.search;

import com.nfteam.server.dto.response.common.PageResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchResponse {

    private List<SearchCollectionResponse> collections;
    private PageResponse<SearchItemResponse> items;

    public SearchResponse(List<SearchCollectionResponse> collections, PageResponse<SearchItemResponse> items) {
        this.collections = collections;
        this.items = items;
    }

}