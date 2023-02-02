package com.nfteam.server.domain.item.repository;

import com.nfteam.server.dto.response.item.ItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ItemRepositoryCustom {

    ItemResponse findItemResponse(Long itemId);

    List<ItemResponse> findItemResponseList(List<Long> itemIdList);

    List<ItemResponse> findItemResponseListByMemberId(Long memberId);

    Page<ItemResponse> findItemResponsePageByCollectionId(Long collectionId, Pageable pageable);

    Slice<ItemResponse> findItemSliceResponseByCollectionId(Long collectionId, Pageable pageable);


}
