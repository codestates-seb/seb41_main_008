package com.nfteam.server.item.repository;

import com.nfteam.server.item.entity.ItemCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<ItemCollection, Long> {

    @Query("select c from ItemCollection c left join fetch c.member where c.collectionId =:id")
    Optional<ItemCollection> findCollectionWithMember(Long id);
}
