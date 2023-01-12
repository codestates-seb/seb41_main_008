package com.nfteam.server.dto.response.member;

import com.nfteam.server.member.entity.MemberStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MemberResponseDto {
    //TODO: cartList, collectionList 추가

    private Long memberId;
    private String email;
    private String nickname;
    private MemberStatus memberStatus;
    private String profileImageName;
    private String createdDate;
    private String modifiedDate;
    private String lastLogin;

    public MemberResponseDto(Long memberId,
                             String email,
                             String nickname,
                             MemberStatus memberStatus,
                             String profileImageName,
                             LocalDateTime createdDate,
                             LocalDateTime modifiedDate,
                             LocalDateTime lastLogin) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.memberStatus = memberStatus;
        this.profileImageName = profileImageName;
        this.createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdDate);
        this.modifiedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(modifiedDate);
        this.lastLogin = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(lastLogin);
    }
}
