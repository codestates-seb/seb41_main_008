package com.nfteam.server.dto.response.search;

import com.nfteam.server.dto.response.common.SliceResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchResponse {

    private List<SearchCollectionResponse> collections;
    private SliceResponse<SearchItemResponse> items;

    public SearchResponse(List<SearchCollectionResponse> collections,
                          SliceResponse<SearchItemResponse> items) {
        this.collections = collections;
        this.items = items;
    }

}