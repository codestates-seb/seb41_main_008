package com.nfteam.server.member.entity;


import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.coin.CoinMemberRel;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.entity.ItemCollection;
import com.nfteam.server.transaction.entity.TransAction;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 400)
    private String password;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "profile_url", length = 2500, nullable = false)
    private String profileUrl;

    // todo: 아래 양방향 연관관계들을 추후 필요없으면 삭제

    // 회원 활성화 여부 - true 활성화, false 탈퇴 회원
    @Column(name = "member_active", nullable = false)
    private Boolean memberActive = true;

    // 현재 진행형 장바구니 + 이전에 장바구니에 담고 결제했던 기록들
    @OneToMany(mappedBy = "member")
    private List<Cart> cartList = new ArrayList<>();

    // 멤버가 소유한(직접 발행) 컬렉션 리스트
    @OneToMany(mappedBy = "member")
    private List<ItemCollection> collectionList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CoinMemberRel> coinList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<TransAction> transActionList = new ArrayList<>();

    protected Member() {
    }

    @Builder
    public Member(Long memberId, String email, String password, String nickname, Boolean memberActive, String profileUrl) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberActive = memberActive;
        this.profileUrl = profileUrl;
    }
}
