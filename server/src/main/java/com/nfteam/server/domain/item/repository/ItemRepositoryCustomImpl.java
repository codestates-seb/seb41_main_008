package com.nfteam.server.domain.item.repository;

import com.nfteam.server.dto.response.item.ItemResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nfteam.server.domain.item.entity.QItem.item;

@Repository
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public ItemResponse findItemResponse(Long itemId) {
        return jpaQueryFactory
                .select(getItemResponseConstructor())
                .from(item)
                .leftJoin(item.collection)
                .leftJoin(item.member)
                .where(item.itemId.eq(itemId))
                .fetchOne();
    }

    @Override
    public List<ItemResponse> findItemResponseList(List<Long> itemIdList) {
        return jpaQueryFactory
                .select(getItemResponseConstructor())
                .from(item)
                .leftJoin(item.collection)
                .leftJoin(item.member)
                .where(item.itemId.in(itemIdList))
                .fetch();
    }

    @Override
    public List<ItemResponse> findItemResponseListByMemberId(Long memberId) {
        return jpaQueryFactory
                .select(getItemResponseConstructor())
                .from(item)
                .leftJoin(item.collection)
                .leftJoin(item.member)
                .where(item.member.memberId.eq(memberId))
                .fetch();
    }

    @Override
    public Page<ItemResponse> findItemResponsePageByCollectionId(Long collectionId, Pageable pageable) {
        QueryResults<ItemResponse> itemResponseQueryResults = jpaQueryFactory
                .select(getItemResponseConstructor())
                .from(item)
                .leftJoin(item.collection)
                .leftJoin(item.member)
                .where(item.collection.collectionId.eq(collectionId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ItemResponse> contents = itemResponseQueryResults.getResults();
        long total = itemResponseQueryResults.getTotal();

        return new PageImpl<>(contents, pageable, total);
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