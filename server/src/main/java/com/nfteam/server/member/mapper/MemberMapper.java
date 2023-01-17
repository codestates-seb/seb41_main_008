package com.nfteam.server.member.mapper;


import com.nfteam.server.dto.request.member.MemberCreateRequest;
import com.nfteam.server.dto.request.member.MemberPatchRequest;
import com.nfteam.server.dto.response.member.MemberResponseDto;
import com.nfteam.server.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member MemberPostDtoToMember(MemberCreateRequest UserPostDto);
    Member MemberPatchDtoToMember(MemberPatchRequest UserPatchDto);
    MemberResponseDto MemberToMemberResponseDto(Member Member);
}
