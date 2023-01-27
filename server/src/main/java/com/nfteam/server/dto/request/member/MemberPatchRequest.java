package com.nfteam.server.dto.request.member;

import lombok.Getter;

@Getter
public class MemberPatchRequest {

    private String nickname;
    private String description;
    private String profileImageName;
    private String bannerImageName;

}
