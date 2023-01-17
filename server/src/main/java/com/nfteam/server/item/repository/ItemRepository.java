package com.nfteam.server.item.repository;

import com.nfteam.server.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i left join fetch i.owner o where i.collection.collectionId =:collectionId")
    List<Item> findItemsByCollectionId(Long collectionId);

    @Query("select i from Item i left join fetch i.owner o where i.itemId =:itemId")
    Optional<Item> findItemWithOwner(Long itemId);

}
