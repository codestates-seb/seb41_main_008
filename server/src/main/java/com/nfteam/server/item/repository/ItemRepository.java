package com.nfteam.server.item.repository;

import com.nfteam.server.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i left join fetch i.member m left join fetch i.itemPrice p where i.itemId =:collectionId")
    List<Item> findItemsByCollectionId(Long collectionId);
}
