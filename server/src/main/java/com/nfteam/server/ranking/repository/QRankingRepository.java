package com.nfteam.server.ranking.repository;

import com.nfteam.server.dto.response.ranking.RankingResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import static com.nfteam.server.batch.entity.QCoinRankingEntity.coinRankingEntity;
import static com.nfteam.server.batch.entity.QTimeRankingEntity.timeRankingEntity;
import static com.nfteam.server.item.entity.QItemCollection.itemCollection;

@Repository
public class QRankingRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public QRankingRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public String getTimeRankString(String timeCriteria) {
        return jpaQueryFactory
                .select(timeRankingEntity.rankString)
                .from(timeRankingEntity)
                .where(timeRankingEntity.rankCriteria.eq(timeCriteria))
                .orderBy(timeRankingEntity.createdDate.desc())
                .fetchFirst();
    }

    public String getCoinRankString(Long coinId) {
        return jpaQueryFactory
                .select(coinRankingEntity.rankString)
                .from(coinRankingEntity)
                .where(coinRankingEntity.coinId.eq(coinId))
                .orderBy(coinRankingEntity.createdDate.desc())
                .fetchFirst();
    }

    public RankingResponse findRankingCollectionInfo(Long collectionId) {
        return jpaQueryFactory
                .select(getRankingResponseConstructor())
                .from(itemCollection)
                .leftJoin(itemCollection.coin)
                .where(itemCollection.collectionId.eq(collectionId))
                .fetchOne();
    }

    private ConstructorExpression<RankingResponse> getRankingResponseConstructor() {
        return Projections.constructor(RankingResponse.class,
                itemCollection.collectionId,
                itemCollection.collectionName,
                itemCollection.logoImgName,
                itemCollection.coin.coinId,
                itemCollection.coin.coinName);
    }

}