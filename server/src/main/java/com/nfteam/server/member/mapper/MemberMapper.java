package com.nfteam.server.member.mapper;


import com.nfteam.server.member.dto.MemberPatchDto;
import com.nfteam.server.member.dto.MemberPostDto;
import com.nfteam.server.member.dto.MemberResponseDto;
import com.nfteam.server.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member MemberPostDtoToMember(MemberPostDto UserPostDto);
    Member MemberPatchDtoToMember(MemberPatchDto UserPatchDto);
    MemberResponseDto MemberToMemberResponseDto(Member Member);
}
