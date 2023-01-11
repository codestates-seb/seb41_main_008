package com.nfteam.server.dto.request.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPatchDto {

    private String nickname;

    private String profileImageName;

    // TODO: 이메일은 바꿀 수 없으므로 지웠습니다.
    // TODO: memberStatus는 회원이 변경하는 것이 아니라서 지웠습니다.
    // TODO: 비밀번호는 별도의 암호화된 로직으로 변경해야 할 것 같아서 일단 여기에서는 지웠습니다.
}
