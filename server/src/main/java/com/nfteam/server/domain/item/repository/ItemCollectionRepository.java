package com.nfteam.server.domain.item.repository;

import com.nfteam.server.domain.item.entity.ItemCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemCollectionRepository extends JpaRepository<ItemCollection, Long>, ItemCollectionRepositoryCustom {

    @Query("select c from ItemCollection c left join fetch c.coin where c.collectionId =:id")
    Optional<ItemCollection> findCollectionWithCoin(Long id);

    @Query("select c from ItemCollection c left join fetch c.member left join fetch c.coin where c.collectionId =:id")
    Optional<ItemCollection> findCollectionWithMemberAndCoin(Long id);

    @Query("select c from ItemCollection c left join fetch c.coin co where c.member.memberId =:id")
    List<ItemCollection> findCollectionListWithCoinByMemberId(Long id);

}