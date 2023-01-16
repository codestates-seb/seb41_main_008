package com.nfteam.server.member.controller;

import com.nfteam.server.security.userdetails.MemberDetails;
import com.nfteam.server.dto.request.member.MemberPatchDto;
import com.nfteam.server.dto.request.member.MemberPostDto;
import com.nfteam.server.dto.response.member.MemberResponseDto;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.mapper.MemberMapper;
import com.nfteam.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/members")
@Validated
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto) {
        Member createdMember = memberService.createMember(memberPostDto);
        return new ResponseEntity<>(MemberResponseDto.of(createdMember), HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Valid @PathVariable("member-id") Long memberId,
                                      @RequestBody MemberPatchDto memberPatchDto,
                                      @AuthenticationPrincipal MemberDetails memberDetails) {
        Member updatedMember = memberService.updateMember(memberPatchDto, memberId, memberDetails.getEmail());
        return new ResponseEntity<>(MemberResponseDto.of(updatedMember), HttpStatus.CREATED);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") Long memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(MemberResponseDto.of(member), HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") Long memberId,
                                       @AuthenticationPrincipal MemberDetails memberDetails) {
        String email = memberDetails.getEmail();
        memberService.deleteMember(memberId, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
