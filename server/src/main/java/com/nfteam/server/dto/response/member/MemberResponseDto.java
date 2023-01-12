package com.nfteam.server.dto.response.member;

import com.nfteam.server.member.entity.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    //TODO: cartlist, grouplist 추가

    private long memberId;

    private String email;

    private String nickname;

    private MemberStatus memberStatus;

    private String profileImageName;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private LocalDateTime lastLogin;

}
