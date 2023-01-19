package com.nfteam.server.transaction.repository;

import com.nfteam.server.transaction.entity.TransAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TransActionRepository extends JpaRepository<TransAction, Long> {

    @Query("select t from TransAction t group by t.collection order by count(t.item) desc")
    Page<TransAction> findByCreatedDateAfter(LocalDateTime localDateTime, Pageable pageable);

}
