package com.nfteam.server.domain.item.repository;

import com.nfteam.server.dto.response.item.CollectionMainResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemCollectionRepositoryCustom {

    Page<CollectionMainResponse> findCollectionOnlyResponsePage(Pageable pageable);

}
