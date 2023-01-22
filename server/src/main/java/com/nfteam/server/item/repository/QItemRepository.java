package com.nfteam.server.item.repository;

import com.nfteam.server.dto.response.item.ItemResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                .leftJoin(item.member)
                .where(item.itemId.eq(itemId))
                .fetchOne();
    }

    public List<ItemResponse> findItemByMember(Long memberId) {
        return jpaQueryFactory
                .select(getItemResponseConstructor())
                .from(item)
                .leftJoin(item.collection)
                .leftJoin(item.member)
                .where(item.member.memberId.eq(memberId))
                .fetch();
    }

    private ConstructorExpression<ItemResponse> getItemResponseConstructor() {
        return Projections.constructor(ItemResponse.class,
                item.collection.collectionId,
                item.collection.collectionName,
                item.member.memberId,
                item.member.nickname,
                item.collection.coin.coinId,
                item.collection.coin.coinName,
                item.collection.coin.withdrawFee,
                item.itemId,
                item.itemName,
                item.itemImageName,
                item.itemDescription,
                item.onSale,
                item.itemPrice);
    }

}