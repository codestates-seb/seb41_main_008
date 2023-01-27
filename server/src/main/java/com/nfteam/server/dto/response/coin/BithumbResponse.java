package com.nfteam.server.dto.response.coin;

import lombok.Getter;

@Getter
public class BithumbResponse<T> {

    // todo: 용도 찾기 , 안쓰는 dto면 삭제
    private String status;
    private T data;

}
