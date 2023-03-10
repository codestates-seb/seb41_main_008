package com.nfteam.server.domain.coin.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;
import java.util.List;

@Getter
public class UpbitWithdrawFee {

    private boolean success;
    private String data;
    private static ObjectMapper mapper = new ObjectMapper();

    public List<UpbitEachWithdrawFee> getData() throws IOException {
        return mapper.readValue(data, new TypeReference<>() {});
    }

}