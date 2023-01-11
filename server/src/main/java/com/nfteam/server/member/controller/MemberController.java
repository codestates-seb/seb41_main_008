package com.nfteam.server.member.controller;

import com.nfteam.server.auth.userdetails.MemberDetails;
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
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberDto) {
        Member member = mapper.MemberPostDtoToMember(memberDto);
        Member createdMember = memberService.createMember(member);

        MemberResponseDto response = mapper.MemberToMemberResponseDto(createdMember);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Valid @PathVariable("member-id") Long memberId,
                                      @RequestBody MemberPatchDto memberDto,
                                      @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = mapper.MemberPatchDtoToMember(memberDto);
        String email = memberDetails.getEmail();
        Member updatedMember = memberService.updateMember(member, email, memberId);

        MemberResponseDto response = mapper.MemberToMemberResponseDto(updatedMember);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/mypage/{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") int memberId,
                                    @AuthenticationPrincipal MemberDetails memberDetails) {
        String email = memberDetails.getEmail();
        Member member = memberService.findMember(memberId, email);

        MemberResponseDto response = mapper.MemberToMemberResponseDto(member);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") int id) {
        Member member = memberService.findMember(id, "");
        MemberResponseDto response = mapper.MemberToMemberResponseDto(member);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") int id,
                                       @AuthenticationPrincipal MemberDetails memberDetails) {
        String email = memberDetails.getEmail();
        memberService.deleteMember(id, email);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
