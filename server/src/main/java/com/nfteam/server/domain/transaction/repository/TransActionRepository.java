package com.nfteam.server.domain.transaction.repository;

import com.nfteam.server.domain.transaction.entity.TransAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransActionRepository extends JpaRepository<TransAction, Long> {
}