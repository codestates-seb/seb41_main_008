package com.nfteam.server.domain.member.entity;

import com.nfteam.server.domain.audit.BaseEntity;
import com.nfteam.server.domain.cart.entity.Cart;
import com.nfteam.server.domain.item.entity.Item;
import com.nfteam.server.domain.item.entity.ItemCollection;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    private static final String DEFAULT_PROFILE_IMAGE = "https://nfteam-dev-img.s3.ap-northeast-2.amazonaws.com/fac08fee-12b4-43f1-a24f-34a382d6fa3f.png";
    private static final String DEFAULT_BANNER_IMAGE = "https://nfteam-dev-img.s3.ap-northeast-2.amazonaws.com/a5dfe9b9-8fb6-43ae-a1ce-533500dae858.jpeg";
    private static final String DEFAULT_DESC = "자기소개를 입력해주세요.";

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
        this.description = DEFAULT_DESC;
        this.memberPlatform = MemberPlatform.HOME;
        this.memberRole = MemberRole.USER;
        this.memberStatus = MemberStatus.MEMBER_ACTIVE;
        this.profileImageName = DEFAULT_PROFILE_IMAGE;
        this.bannerImageName = DEFAULT_BANNER_IMAGE;
        this.lastLoginTime = LocalDateTime.now();
    }

    // 소셜 로그인용 생성자
    public Member(String email, String nickname, MemberPlatform memberPlatform) {
        this.email = email;
        this.password = UUID.randomUUID().toString();
        this.nickname = nickname;
        this.description = DEFAULT_DESC;
        this.memberPlatform = memberPlatform;
        this.memberRole = MemberRole.USER;
        this.memberStatus = MemberStatus.MEMBER_ACTIVE;
        this.profileImageName = DEFAULT_PROFILE_IMAGE;
        this.bannerImageName = DEFAULT_BANNER_IMAGE;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return memberId.equals(member.memberId) && email.equals(member.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, email);
    }

}