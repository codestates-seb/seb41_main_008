package com.nfteam.server.item.repository;

import com.nfteam.server.dto.response.search.SearchCollectionResponse;
import com.nfteam.server.dto.response.search.SearchItemResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.nfteam.server.item.entity.QItem.item;
import static com.nfteam.server.item.entity.QItemCollection.itemCollection;

@Repository
@RequiredArgsConstructor
public class QSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SearchCollectionResponse> searchCollectionWithKeyword(String keyword) {
        return jpaQueryFactory
                .select(getSearchCollectionResponseConstructor())
                .from(itemCollection)
                .where(containsExpression(itemCollection.collectionName, keyword))
                .limit(10)
                .orderBy(itemCollection.createdDate.desc())
                .fetch();
    }

    public Page<SearchItemResponse> searchItemWithKeyword(String keyword, Pageable pageable) {
        QueryResults<SearchItemResponse> searchItemResponseQueryResults
                = jpaQueryFactory
                .select(getSearchItemResponseConstructor())
                .from(item)
                .leftJoin(item.collection)
                .leftJoin(item.collection.coin)
                .where(containsExpression(item.itemName, keyword)
                        .or(containsExpression(item.collection.collectionName, keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<SearchItemResponse> contents = searchItemResponseQueryResults.getResults();
        long total = searchItemResponseQueryResults.getTotal();

        return new PageImpl<>(contents, pageable, total);
    }

    private ConstructorExpression<SearchCollectionResponse> getSearchCollectionResponseConstructor() {
        return Projections.constructor(SearchCollectionResponse.class,
                itemCollection.collectionId,
                itemCollection.collectionName,
                itemCollection.logoImgName,
                itemCollection.bannerImgName);
    }

    private ConstructorExpression<SearchItemResponse> getSearchItemResponseConstructor() {
        return Projections.constructor(SearchItemResponse.class,
                item.collection.collectionId,
                item.collection.collectionName,
                item.collection.coin.coinId,
                item.collection.coin.coinName,
                item.itemId,
                item.itemName,
                item.itemImageName,
                item.onSale,
                item.itemPrice);
    }

    private BooleanExpression containsExpression(StringPath stringPath, String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        }
        return stringPath.contains(keyword);
    }

}