package com.nfteam.server.member.dto;

import com.nfteam.server.member.entity.Member.MemberStatus;
import java.time.LocalDateTime;
import java.util.List;
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
public class MemberResponseDto {
    private long memberId;

    @NotBlank
    @Email
    private String email;

    private String nickname;

    private MemberStatus memberStatus;

    private String profileUrl;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private LocalDateTime lastLogin;

}
