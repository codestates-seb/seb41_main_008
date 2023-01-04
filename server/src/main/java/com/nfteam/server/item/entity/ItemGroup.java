package com.nfteam.server.item.entity;

import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "item_collection_group")
public class ItemGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name", length = 100)
    private String groupName;

    // 해당 그룹(컬렉션) 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member owner;

    // 해당 아이템 그룹에 속한 아이템 리스트
    @OneToMany(mappedBy = "group")
    private List<Item> itemList = new ArrayList<>();

    protected ItemGroup() {
    }

    public void assignMember(Member member) {
        this.owner = member;
        member.getGroupList().add(this);
    }


}
