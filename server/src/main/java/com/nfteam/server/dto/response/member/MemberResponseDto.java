package com.nfteam.server.dto.response.member;

import com.nfteam.server.domain.member.entity.Member;
import com.nfteam.server.domain.member.entity.MemberStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MemberResponseDto {

    private Long memberId;
    private String email;
    private String nickname;
    private String description;
    private MemberStatus memberStatus;
    private String profileImageName;
    private String bannerImageName;
    private String createdDate;
    private String modifiedDate;
    private String lastLogin;

    @Builder
    public MemberResponseDto(Long memberId,
                             String email,
                             String nickname,
                             String description,
                             MemberStatus memberStatus,
                             String profileImageName,
                             String bannerImageName,
                             LocalDateTime createdDate,
                             LocalDateTime modifiedDate,
                             LocalDateTime lastLogin) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.description = description;
        this.memberStatus = memberStatus;
        this.profileImageName = profileImageName;
        this.bannerImageName = bannerImageName;
        this.createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdDate);
        this.modifiedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(modifiedDate);
        this.lastLogin = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(lastLogin);
    }

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .description(member.getDescription())
                .memberStatus(member.getMemberStatus())
                .profileImageName(member.getProfileImageName())
                .bannerImageName(member.getBannerImageName())
                .createdDate(member.getCreatedDate())
                .modifiedDate(member.getModifiedDate())
                .lastLogin(member.getLastLoginTime())
                .build();
    }

}