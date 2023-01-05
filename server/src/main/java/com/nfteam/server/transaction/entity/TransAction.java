package com.nfteam.server.transaction.entity;

import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import static com.nfteam.server.transaction.entity.TransType.*;

@Getter
@Entity
@Table(name = "transaction")
public class TransAction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_id")
    private Long transId;

    // 0 - SALE 1 - BUY
    @Column(name = "trans_type")
    private Integer transType = SALE.getValue();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    protected TransAction() {
    }

    @Builder
    public TransAction(Integer transType, Member member, Item item) {
        this.transType = transType;
        this.member = member;
        this.item = item;
    }
}
