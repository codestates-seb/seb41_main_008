package com.nfteam.server.domain.item.repository;

import com.nfteam.server.domain.item.entity.ItemCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCredentialRepository extends JpaRepository<ItemCredential, Long> {
}