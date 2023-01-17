package com.nfteam.server.dto.response.member;

import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.entity.MemberStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MemberMyPageResponseDto {

    private MemberResponseDto member;

}
