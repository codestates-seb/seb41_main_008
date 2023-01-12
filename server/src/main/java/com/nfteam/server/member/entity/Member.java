package com.nfteam.server.member.entity;

import com.nfteam.server.audit.BaseEntity;
import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.entity.ItemCollection;

import java.time.LocalDateTime;

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

    @Column(name = "profile_image", length = 2500)
    private String profileImage;

    @Column(name = "last_login")
    private LocalDateTime lastLoginTime;

    // 회원 상태
    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_status", length = 50, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    // 회원 가입 경로
    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_platform", length = 50, nullable = false)
    private MemberPlatform memberPlatform = MemberPlatform.HOME;

    // 회원 등급
    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_role", length = 50, nullable = false)
    private MemberRole memberRole = MemberRole.USER;

    // 멤버 장바구니 리스트
    @OneToMany(mappedBy = "member")
    private List<Cart> cartList = new ArrayList<>();

    // 멤버가 가진 컬렉션 리스트
    @OneToMany(mappedBy = "member")
    private List<ItemCollection> collectionList = new ArrayList<>();

    // 멤버 소유 NFT 아이템 리스트
    @OneToMany(mappedBy = "member")
    private List<Item> itemList = new ArrayList<>();

    protected Member() {
    }

    public Member(Long memberId) {
        this.memberId = memberId;
    }

    public void updateLastLoginTime() {
        this.lastLoginTime = LocalDateTime.now();
    }

    public void updateMemberStatusQuit() {
        this.memberStatus = MemberStatus.MEMBER_QUIT;
    }

    public void updateNickname(String name) {
        this.nickname = name;
    }

    public void updateProfileImg(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateCreateInfo(String encryptedPassword) {
        this.password = encryptedPassword;
        this.memberRole = MemberRole.USER;
        this.profileImage = "default-profile-image";
    }
}