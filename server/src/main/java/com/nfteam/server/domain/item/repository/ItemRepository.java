package com.nfteam.server.domain.item.repository;

import com.nfteam.server.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    @Query("select i from Item i left join fetch i.member m where i.collection.collectionId =:collectionId")
    List<Item> findItemsWithOwnerByCollectionId(Long collectionId);

    @Query("select i from Item i left join fetch i.member m where i.itemId =:itemId")
    Optional<Item> findItemWithOwner(Long itemId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
    @Query("select i from Item i " +
            "left join fetch i.itemCredential ic " +
            "left join fetch i.member m " +
            "left join fetch i.collection c " +
            "where i.itemId =:itemId")
    Optional<Item> findItemWithOwnerAndCredentialAndCollection(Long itemId);

    @Query("select i from Item i where i.collection.collectionId =:collectionId")
    List<Item> findItemsByCollectionId(Long collectionId);

}