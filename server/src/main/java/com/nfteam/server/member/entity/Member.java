package com.nfteam.server.member.entity;


import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.item.entity.ItemCollection;
import java.time.LocalDateTime;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "member")
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 400)
    private String password;

    @Column(name = "nickname", nullable = false, length = 100)
    private String nickname;

    // 프로필 이미지 URL
    @Column(name = "profile_url", length = 2500, nullable = false)
    private String profileUrl;

    //회원상태관리를 위한 Column
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_status",length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    // 멤버의 장바구니 리스트
    // 현재 장바구니 + 이전에 장바구니에 담은 기록들
    // 멤버의 장바구니를 조회하는 api 호출시 현재 활성화 된 장바구니를 보여주도록 해야한다.
    @OneToMany(mappedBy = "member")
    private List<Cart> cartList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    // 멤버가 가진 그룹 리스트
    @OneToMany(mappedBy = "member")
    private List<ItemCollection> collectionList = new ArrayList<>();

    // 해당 멤버가 소유한 아이템을 조회하고 싶다면
    // 연관관계 상 아이템쪽에서 memberId 를 조건으로 꺼내와야 합니다.

    /** 회원가입 - 활동중
     *  1년 이상 로그인 x - 휴면상태
     *  회원탈퇴 - 탈퇴 상태
     */
    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

    public enum OauthPlatform {
        GOOGLE, GITHUB;
    }
}
