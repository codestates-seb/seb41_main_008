package com.nfteam.server.dto;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.nfteam.server.Domain.Member} entity
 */
@Data
public class MemberDto implements Serializable {
    private String userId;
    private String userPassword;
    private String email;
    private String nickname;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;





}