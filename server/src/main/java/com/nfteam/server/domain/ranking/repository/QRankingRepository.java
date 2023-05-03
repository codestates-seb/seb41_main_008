package com.nfteam.server.domain.ranking.repository;

import com.nfteam.server.dto.response.ranking.RankingResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.nfteam.server.domain.item.entity.QItemCollection.itemCollection;
import static com.nfteam.server.domain.ranking.entity.QDailyAggregate.dailyAggregate;


@Repository
public class QRankingRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public QRankingRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // 시간대별 컬렉션 랭킹 조회
    public List<Long> getTimeRankCollectionId(LocalDate date) {
        return jpaQueryFactory
                .select(dailyAggregate.collection.collectionId)
                .from(dailyAggregate)
                .where(dailyAggregate.baseDate.after(date))
                .groupBy(dailyAggregate.collection.collectionId)
                .orderBy(dailyAggregate.totalTradingVolume.desc())
                .fetch();
    }

    // 코인 별 컬렉션 랭킹 조회(연간 기준)
    public List<Long> getCoinRankCollectionId(Long coinId) {
        return jpaQueryFactory
                .select(dailyAggregate.collection.collectionId)
                .from(dailyAggregate)
                .where(dailyAggregate.baseDate.after(LocalDate.now().minusYears(1L))
                        .and(dailyAggregate.coin.coinId.eq(coinId)))
                .groupBy(dailyAggregate.collection)
                .orderBy(dailyAggregate.totalTradingVolume.desc())
                .fetch();
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
                itemCollection.coin.coinName,
                itemCollection.coin.coinImage);
    }

}