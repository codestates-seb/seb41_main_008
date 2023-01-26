package com.nfteam.server.dto.response.common;

import lombok.Getter;

@Getter
public class SingleIdResponse {

    private String status;
    private Long id;

    public SingleIdResponse(String status, Long id) {
        this.status = status;
        this.id = id;
    }

}
