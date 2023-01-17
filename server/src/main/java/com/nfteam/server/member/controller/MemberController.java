package com.nfteam.server.member.controller;

import com.nfteam.server.dto.request.member.MemberCreateRequest;
import com.nfteam.server.dto.request.member.MemberPatchRequest;
import com.nfteam.server.dto.response.common.SingleIdResponse;
import com.nfteam.server.dto.response.member.MemberResponseDto;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.service.MemberService;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberCreateRequest memberCreateRequest) {
        Long savedId = memberService.createMember(memberCreateRequest);
        return new ResponseEntity<>(new SingleIdResponse(HttpStatus.CREATED.name(), savedId), HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") Long memberId,
                                      @RequestBody MemberPatchRequest memberPatchRequest,
                                      @AuthenticationPrincipal MemberDetails memberDetails) {
        Long updatedId = memberService.updateMember(memberPatchRequest, memberId, memberDetails.getEmail());
        return new ResponseEntity<>(new SingleIdResponse(HttpStatus.OK.name(), updatedId), HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") Long memberId) {
        return new ResponseEntity<>(
                MemberResponseDto.of(memberService.findMember(memberId)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") Long memberId,
                                       @AuthenticationPrincipal MemberDetails memberDetails) {
        String email = memberDetails.getEmail();
        memberService.deleteMember(memberId, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
