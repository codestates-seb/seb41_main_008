package com.nfteam.server.item.repository;

import com.nfteam.server.dto.response.item.ItemResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.nfteam.server.item.entity.QItem.item;

@Repository
@RequiredArgsConstructor
public class QItemRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemResponse findItem(Long itemId) {
        return jpaQueryFactory
                .select(getItemResponseConstructor())
                .from(item)
                .leftJoin(item.collection)
                .leftJoin(item.owner)
                .where(item.itemId.eq(itemId))
                .fetchOne();
    }

    private ConstructorExpression<ItemResponse> getItemResponseConstructor() {
        return Projections.constructor(ItemResponse.class,
                item.collection.collectionId,
                item.collection.collectionName,
                item.owner.memberId,
                item.owner.nickname,
                item.collection.coin.coinId,
                item.collection.coin.coinName,
                item.collection.coin.withdrawlFee,
                item.itemId,
                item.itemName,
                item.itemImageName,
                item.onSale,
                item.itemPrice);
    }


}
