package com.nfteam.server.transaction.repository;

import com.nfteam.server.transaction.entity.TransAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TransActionRepository extends JpaRepository<TransAction, Long> {

    Page<TransAction> findTransActionByCreatedDateAfter(LocalDateTime localDateTime, Pageable pageable);

}
