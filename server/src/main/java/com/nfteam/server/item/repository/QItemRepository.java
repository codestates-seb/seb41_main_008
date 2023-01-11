//package com.nfteam.server.item.repository;
//
//import com.nfteam.server.coin.QCoin;
//import com.nfteam.server.item.entity.Item;
//import com.nfteam.server.item.entity.QItem;
//import com.nfteam.server.item.entity.QItemPrice;
//import com.querydsl.core.Tuple;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class QItemRepository {
//
//    private final QItem item = QItem.item;
//    private final QItemPrice price = QItemPrice.itemPrice;
//    private final QCoin coin = QCoin.coin;
//    private final JPAQueryFactory jpaQueryFactory;
//
//    public QItemRepository(JPAQueryFactory jpaQueryFactory) {
//        this.jpaQueryFactory = jpaQueryFactory;
//    }
//
//    public List<Tuple> findItems(Long collectionId) {
//        return jpaQueryFactory
//                .select(item, price, coin)
//                .from(item)
//                .leftJoin(item.itemPrice, price)
//                .leftJoin(item.itemPrice.coin, coin)
//                .fetch();
//    }
//}
