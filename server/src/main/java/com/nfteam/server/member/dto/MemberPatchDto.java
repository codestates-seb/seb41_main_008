package com.nfteam.server.member.dto;

import com.nfteam.server.member.entity.Member.MemberStatus;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPatchDto {
    private long memberId;

    @Email
    private String email;

    private String nickname;

    private MemberStatus memberStatus;

    private String password;

    private String profileUrl;
}
