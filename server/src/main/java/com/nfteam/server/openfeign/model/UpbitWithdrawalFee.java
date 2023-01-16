package com.nfteam.server.openfeign.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpbitWithdrawalFee {

    private boolean success;
    private String data;

    private static ObjectMapper mapper = new ObjectMapper();

    public List<UpbitEachWithdrawalFee> getData() throws IOException {
        return mapper.readValue(data, new TypeReference<>() {});
    }

}
