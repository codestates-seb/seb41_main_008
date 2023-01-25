package com.nfteam.server.dto.request.ranking;

import lombok.Getter;

import java.util.List;

@Getter
public class RankingListRequest {

    private List<Long> rankList;

    private RankingListRequest() {
    }

}