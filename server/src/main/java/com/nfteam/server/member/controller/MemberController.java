package com.nfteam.server.member.controller;

import com.nfteam.server.member.dto.MemberPostDto;
import com.nfteam.server.member.dto.MemberResponseDto;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.mapper.MemberMapper;
import com.nfteam.server.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

}
