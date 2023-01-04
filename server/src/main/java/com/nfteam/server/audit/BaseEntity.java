package com.nfteam.server.audit;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;

    // 생성한 사람을 등록하고 수정한 사람를 자동으로 등록하는 부분은 스프링 시큐리티 컨텍스트에서 연동해서 가져와야함
    // AuditorAware 적용이 필요해서 구현해야 하고, 더불어 현재 애플리케이션에서는 이미 유저 정보가 다 연관되어 있어서 불필요한 정보라서 제외
}
