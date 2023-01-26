package com.nfteam.server.dto.request.member;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class MemberCreateRequest {

    @Email
    @NotBlank(message = "이메일은 필수값 입니다.")
    private String email;

    @NotBlank(message = "이름을 정해주세요")
    private String nickname;

    @NotBlank(message = "비밀번호를 정해주세요")
    private String password;

}
