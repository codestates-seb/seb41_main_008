package com.nfteam.server.member.entity;


import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.entity.ItemCollection;

import java.time.LocalDateTime;


import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Setter;


//TODO Setter,Mapstruct 리팩토링
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

    @Column(name = "nickname", length = 100)
    private String nickname;

    // 프로필 이미지 URL
    @Column(name = "profile_image", length = 2500)
    private String profileImageName;

    //회원상태관리를 위한 Column
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_status", length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    // 멤버의 장바구니 리스트
    // 현재 장바구니 + 이전에 장바구니에 담은 기록들
    // 멤버의 장바구니를 조회하는 api 호출시 현재 활성화 된 장바구니를 보여주도록 해야한다.
    @OneToMany(mappedBy = "member")
    private List<Cart> cartList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> roles = new ArrayList<>();

    // 멤버가 가진 그룹 리스트
    @OneToMany(mappedBy = "member")
    private List<ItemCollection> groupList = new ArrayList<>();

    // 멤버가 가진 아이템 리스트
    @OneToMany(mappedBy = "member")
    private List<Item> itemList = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Member(Long memberId) {
        this.memberId = memberId;
    }

    public void changeRole(Role role) {
        this.role=role;

    }

    public void changeProfile(String ProfileImage) {
        this.profileUrl=ProfileImage;
    }


    /** 회원가입 - 활동중
     *  1년 이상 로그인 x - 휴면상태
     *  회원탈퇴 - 탈퇴 상태
    /**
     * 회원가입 - 활동중
     * 1년 이상 로그인 x - 휴면상태
     * 회원탈퇴 - 탈퇴 상태
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


    public static Member transToGoogle(String email) {
        return Member.builder()
                .email(email)
                .nickname(email.substring(0,email.indexOf("@"))) //이메일에서 앞부분을 계정 정보로 가져옴
                .build();
    }
    @Builder
    public Member(String nickname, String email, String profileUrl, Role role){
        this.nickname=nickname;
        this.email=email;
        this.profileUrl=profileUrl;
        this.role=role;

    }

    // Oauth 리 다이렉트 시 업데이트 할 이름, 프로필
    public Member update(String name, String profileUrl){
        this.nickname=name;
        this.profileUrl=profileUrl;
        return this;
    }
    // Role의 키를 가져오는 역할
    public String getRoleKey(){
        return this.role.getKey();
    }
}
