package com.nfteam.server.transaction.repository;

import com.nfteam.server.transaction.entity.TransAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransActionRepository extends JpaRepository<TransAction, Long> {
}
