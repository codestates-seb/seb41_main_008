package com.nfteam.server.dto.request.auth;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class LoginDto {
    @Email
    @NotNull(message = "이메일은 필수 값 입니다.")
    private String email;
    @NotNull(message = "비밀번호는 필수 값 입니다.")
    private String password;

}
