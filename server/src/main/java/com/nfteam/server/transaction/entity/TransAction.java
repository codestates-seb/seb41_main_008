package com.nfteam.server.transaction.entity;

import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "transaction")
public class TransAction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_id")
    private Long transId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // true(1) - 구매, false(0) - 판매
    @Column(name = "trans_type")
    private Boolean transType;

    protected TransAction() {
    }

}
