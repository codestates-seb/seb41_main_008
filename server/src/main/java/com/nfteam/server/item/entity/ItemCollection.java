package com.nfteam.server.item.entity;

import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "item_collection")
public class ItemCollection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private Long collectionId;

    @Column(name = "name", length = 100)
    private String collectionName;

    // 해당 그룹(컬렉션) 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 해당 아이템 그룹에 속한 아이템 리스트
    @OneToMany(mappedBy = "collection")
    private List<Item> itemList = new ArrayList<>();

    protected ItemCollection() {
    }

    public void assignMember(Member member) {
        this.member = member;
        member.getGroupList().add(this);
    }


}
