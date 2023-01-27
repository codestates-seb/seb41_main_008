package com.nfteam.server.member.entity;

import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.common.audit.BaseEntity;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.entity.ItemCollection;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Column(name = "member_desc", length = 3000)
    private String description;

    @Column(name = "profile_image_name", length = 2500)
    private String profileImageName;

    @Column(name = "banner_image_name", length = 2500)
    private String bannerImageName;

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

    // 멤버가 만든 컬렉션 리스트
    @OneToMany(mappedBy = "member")
    private List<ItemCollection> collectionList = new ArrayList<>();

    // 멤버 소유 NFT 아이템 리스트
    @OneToMany(mappedBy = "member")
    private List<Item> itemList = new ArrayList<>();

    public Member() {
    }

    // 연관관계 입력용 생성자
    public Member(Long memberId) {
        this.memberId = memberId;
    }

    // 신규 회원용 생성자
    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.description = "자기소개를 입력해주세요.";
        this.memberPlatform = MemberPlatform.HOME;
        this.memberRole = MemberRole.USER;
        this.memberStatus = MemberStatus.MEMBER_ACTIVE;
        this.profileImageName = "https://nfteam-dev-img.s3.ap-northeast-2.amazonaws.com/460a3185-7e27-48ed-a23f-9e87e5686468.jpeg";
        this.bannerImageName = "https://nfteam-dev-img.s3.ap-northeast-2.amazonaws.com/5970bd4a-b60e-4d08-a8c4-0dc6bc61e1f5.jpeg";
        this.lastLoginTime = LocalDateTime.now();
    }

    // 소셜 로그인용 생성자
    public Member(String email, String nickname, MemberPlatform memberPlatform) {
        this.email = email;
        this.password = UUID.randomUUID().toString();
        this.nickname = nickname;
        this.description = "자기소개를 입력해주세요.";
        this.memberPlatform = memberPlatform;
        this.memberRole = MemberRole.USER;
        this.memberStatus = MemberStatus.MEMBER_ACTIVE;
        this.profileImageName = "https://nfteam-dev-img.s3.ap-northeast-2.amazonaws.com/460a3185-7e27-48ed-a23f-9e87e5686468.jpeg";
        this.bannerImageName = "https://nfteam-dev-img.s3.ap-northeast-2.amazonaws.com/5970bd4a-b60e-4d08-a8c4-0dc6bc61e1f5.jpeg";
        this.lastLoginTime = LocalDateTime.now();
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

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateProfileImg(String profileImage) {
        this.profileImageName = profileImage;
    }

    public void updateBannerImg(String bannerImage) {
        this.bannerImageName = bannerImage;
    }

}