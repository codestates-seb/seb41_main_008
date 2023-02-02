package com.nfteam.server.domain.transaction.repository;

import com.nfteam.server.dto.response.item.ItemTradeHistoryResponse;

import java.util.List;

public interface TransActionRepositoryCustom {

    List<ItemTradeHistoryResponse> findTradeHistory(Long itemId);

}
