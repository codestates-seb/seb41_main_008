package com.nfteam.server.batch.repository;

import com.nfteam.server.batch.entity.Ranking6H;
import com.nfteam.server.transaction.entity.TransAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface Ranking6HRepository extends JpaRepository<Ranking6H, Long> {
}
