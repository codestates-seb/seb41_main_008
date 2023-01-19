package com.nfteam.server.dto.request.cart;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CartPatchDto {

    @NotNull
    private List<Long> ItemIdList;

}
