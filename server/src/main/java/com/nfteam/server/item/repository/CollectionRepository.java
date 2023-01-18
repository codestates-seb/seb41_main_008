package com.nfteam.server.item.repository;

import com.nfteam.server.item.entity.ItemCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<ItemCollection, Long> {

    @Query("select c from ItemCollection c left join fetch c.coin where c.collectionId =:id")
    Optional<ItemCollection> findCollectionWithCoin(Long id);

    @Query("select c from ItemCollection c left join fetch c.member left join fetch c.coin where c.collectionId =:id")
    Optional<ItemCollection> findCollectionWithMemberAndCoin(Long id);

    @Query("select c from ItemCollection c left join fetch c.coin co where c.member.memberId =:id")
    List<ItemCollection> findCollectionWithCoinByMemberId(Long id);

}
