package com.nfteam.server.item.repository;

import com.nfteam.server.item.entity.ItemCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<ItemCollection, Long> {
}
