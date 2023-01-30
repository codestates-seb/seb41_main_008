package com.nfteam.server.domain.item.repository;

import com.nfteam.server.dto.response.item.CollectionMainResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nfteam.server.domain.item.entity.QItemCollection.itemCollection;

@Repository
public class ItemCollectionRepositoryCustomImpl implements ItemCollectionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemCollectionRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<CollectionMainResponse> findCollectionOnlyResponsePage(Pageable pageable) {
        QueryResults<CollectionMainResponse> itemResponseQueryResults = jpaQueryFactory
                .select(getCollectionOnlyResponseConstructor())
                .from(itemCollection)
                .leftJoin(itemCollection.member)
                .leftJoin(itemCollection.coin)
                .orderBy(itemCollection.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CollectionMainResponse> contents = itemResponseQueryResults.getResults();
        long total = itemResponseQueryResults.getTotal();

        return new PageImpl<>(contents, pageable, total);
    }

    private ConstructorExpression<CollectionMainResponse> getCollectionOnlyResponseConstructor() {
        return Projections.constructor(CollectionMainResponse.class,
                itemCollection.collectionId,
                itemCollection.collectionName,
                itemCollection.description,
                itemCollection.logoImgName,
                itemCollection.bannerImgName,
                itemCollection.createdDate,
                itemCollection.member.memberId,
                itemCollection.member.nickname,
                itemCollection.coin.coinId,
                itemCollection.coin.coinName,
                itemCollection.coin.coinImage);
    }

}