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

    // REST API 규칙 상 주소줄에 동사는 넣지 않고 HTTPMETHOD로 상태를 표시합니다.
    // 여기서는 그냥 api/members 와 post 메서드를 사용하는 것만으로 회원가입을 나타냅니다.
    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberDto){
        Member member = mapper.MemberPostDtoToMember(memberDto);

        Member createdMember = memberService.createMember(member);
        MemberResponseDto response = mapper.MemberToMemberResponseDto(createdMember);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }




}
