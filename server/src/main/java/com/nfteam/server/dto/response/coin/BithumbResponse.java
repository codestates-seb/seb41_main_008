package com.nfteam.server.dto.response.coin;

import lombok.Getter;

@Getter
public class BithumbResponse<T> {
    private String status;
    private T data;

}
