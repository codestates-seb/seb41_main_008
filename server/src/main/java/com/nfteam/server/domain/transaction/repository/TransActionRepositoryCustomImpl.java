package com.nfteam.server.domain.transaction.repository;

import com.nfteam.server.dto.response.item.ItemTradeHistoryResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nfteam.server.domain.transaction.entity.QTransAction.transAction;

@Repository
public class TransActionRepositoryCustomImpl implements TransActionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public TransActionRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ItemTradeHistoryResponse> findTradeHistory(Long itemId) {
        return jpaQueryFactory
                .select(getItemTradeResponseConstructor())
                .from(transAction)
                .leftJoin(transAction.seller)
                .leftJoin(transAction.buyer)
                .leftJoin(transAction.coin)
                .where(transAction.item.itemId.eq(itemId))
                .orderBy(transAction.createdDate.desc())
                .limit(10)
                .fetch();
    }

    private ConstructorExpression<ItemTradeHistoryResponse> getItemTradeResponseConstructor() {
        return Projections.constructor(ItemTradeHistoryResponse.class,
                transAction.seller.memberId,
                transAction.seller.nickname,
                transAction.buyer.memberId,
                transAction.buyer.nickname,
                transAction.transPrice,
                transAction.coin.coinName,
                transAction.createdDate);
    }

}