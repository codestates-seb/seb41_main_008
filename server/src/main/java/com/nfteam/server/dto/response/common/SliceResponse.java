package com.nfteam.server.dto.response.common;

import lombok.Getter;

import java.util.List;

@Getter
public class SliceResponse<T> {

    private Boolean hasNext;
    private List<T> data;

    private SliceResponse() {
    }

    public SliceResponse(Boolean hasNext, List<T> data) {
        this.hasNext = hasNext;
        this.data = data;
    }

}