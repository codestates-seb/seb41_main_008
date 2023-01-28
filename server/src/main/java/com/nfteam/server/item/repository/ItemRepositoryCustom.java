package com.nfteam.server.item.repository;

import com.nfteam.server.dto.response.item.ItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {

    ItemResponse findItemResponse(Long itemId);

    List<ItemResponse> findItemResponseList(List<Long> itemIdList);

    List<ItemResponse> findItemResponseListByMemberId(Long memberId);

    Page<ItemResponse> findItemResponsePageByCollectionId(Long collectionId, Pageable pageable);

}
