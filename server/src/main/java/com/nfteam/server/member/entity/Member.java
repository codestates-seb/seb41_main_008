package com.nfteam.server.member.entity;


import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.item.entity.ItemGroup;
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

    // 회원 활성화 여부
    // true 활성화 회원, false 탈퇴 회원
    @Column(name = "member_active", nullable = false)
    private Boolean memberActive = true;

    // 프로필 이미지 URL
    @Column(name = "profile_url", length = 2500, nullable = false)
    private String profileUrl;

    // 멤버의 장바구니 리스트
    // 현재 장바구니 + 이전에 장바구니에 담은 기록들
    // 멤버의 장바구니를 조회하는 api 호출시 현재 활성화 된 장바구니를 보여주도록 해야한다.
    @OneToMany(mappedBy = "member")
    private List<Cart> cartList = new ArrayList<>();

    // 멤버가 가진 그룹 리스트
    @OneToMany(mappedBy = "owner")
    private List<ItemGroup> groupList = new ArrayList<>();

    // 해당 멤버가 소유한 아이템을 조회하고 싶다면
    // 연관관계 상 아이템쪽에서 memberId 를 조건으로 꺼내와야 합니다.

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
