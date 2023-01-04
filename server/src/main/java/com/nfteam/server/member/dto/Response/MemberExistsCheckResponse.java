package com.nfteam.server.member.dto.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberExistsCheckResponse {
    private boolean success;
    private String message;

    @Builder
    public MemberExistsCheckResponse(Boolean success, String message){
        this.message=message;
        this.success=success;
    }
}
