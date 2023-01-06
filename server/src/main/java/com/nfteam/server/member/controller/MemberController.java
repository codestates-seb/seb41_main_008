package com.nfteam.server.member.controller;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.member.dto.MemberPostDto;
import com.nfteam.server.member.dto.MemberResponseDto;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.mapper.MemberMapper;
import com.nfteam.server.member.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@Validated
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberDto){
        Member member = mapper.MemberPostDtoToMember(memberDto);

        Member createdMember = memberService.createMember(member);
        MemberResponseDto response = mapper.MemberToMemberResponseDto(createdMember);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
    @DeleteMapping("/logout")
    public ResponseEntity logoutMember(HttpServletRequest request,@AuthenticationPrincipal MemberDetails memberDetails){
        memberService.logout(request,memberDetails.getMemberId());

        return new ResponseEntity("로그아웃 되었습니다.",HttpStatus.OK);

    }

}
