package com.nfteam.server.domain.item.repository;

import com.nfteam.server.dto.response.search.SearchCollectionResponse;
import com.nfteam.server.dto.response.search.SearchItemResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.nfteam.server.domain.item.entity.QItem.item;
import static com.nfteam.server.domain.item.entity.QItemCollection.itemCollection;

@Repository
public class QSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public QSearchRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<SearchCollectionResponse> searchCollectionWithKeyword(String keyword) {
        return jpaQueryFactory
                .select(getSearchCollectionResponseConstructor())
                .from(itemCollection)
                .where(containsExpression(itemCollection.collectionName, keyword))
                .limit(10)
                .orderBy(itemCollection.createdDate.desc())
                .fetch();
    }

    public Slice<SearchItemResponse> searchItemWithKeyword(String keyword, Pageable pageable) {
        List<SearchItemResponse> responses = jpaQueryFactory
                .select(getSearchItemResponseConstructor())
                .from(item)
                .leftJoin(item.collection)
                .leftJoin(item.collection.coin)
                .where(containsExpression(item.itemName, keyword)
                        .or(containsExpression(item.collection.collectionName, keyword)))
                .orderBy(item.itemId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return toSlice(pageable, responses);
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
                item.collection.coin.coinImage,
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
        return stringPath.containsIgnoreCase(keyword);
    }

    // todo: Utils 클래스로 중복 메서드 공통화 하기
    private <T> Slice<T> toSlice(Pageable pageable, List<T> items) {
        if (items.size() > pageable.getPageSize()) {
            items.remove(items.size() - 1);
            return new SliceImpl<>(items, pageable, true);
        }
        return new SliceImpl<>(items, pageable, false);
    }

}