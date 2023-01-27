package com.nfteam.server.dto.response.member;

import com.nfteam.server.dto.response.item.ItemResponse;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberMyPageResponseDto {

    private MemberResponseDto member;
    private List<MemberItemResponse> items;
    private List<MemberCollectionResponse> collections;

    public MemberMyPageResponseDto(MemberResponseDto member, List<ItemResponse> items, List<MemberCollectionResponse> collections) {
        this.member = member;
        this.collections = collections;
        this.items = items.stream()
                .map(i -> MemberItemResponse.of(i))
                .collect(Collectors.toList());
    }

}