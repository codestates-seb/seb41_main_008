package com.nfteam.server.member.controller;

import com.nfteam.server.auth.userdetails.MemberDetails;
import com.nfteam.server.dto.request.member.MemberPatchDto;
import com.nfteam.server.dto.request.member.MemberPostDto;
import com.nfteam.server.dto.response.member.MemberResponseDto;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.mapper.MemberMapper;
import com.nfteam.server.member.service.MemberService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberDto) {
        Member member = mapper.MemberPostDtoToMember(memberDto);

        Member createdMember = memberService.createMember(member);
        MemberResponseDto response = mapper.MemberToMemberResponseDto(createdMember);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Valid @PathVariable("member-id") int id,
        @RequestBody MemberPatchDto memberDto,
        @AuthenticationPrincipal MemberDetails memberDetails) {

        Member member = mapper.MemberPatchDtoToMember(memberDto);
        String email = memberDetails.getEmail();

        Member updatedMember = memberService.updateMember(member, email);

        MemberResponseDto response = mapper.MemberToMemberResponseDto(updatedMember);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/mypage/{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") int id
        , @AuthenticationPrincipal MemberDetails memberDetails) {

        String email = memberDetails.getEmail();

        Member member = memberService.findMember(id, email);

        MemberResponseDto response = mapper.MemberToMemberResponseDto(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") int id
    ) {

        Member member = memberService.findMember(id, "");

        MemberResponseDto response = mapper.MemberToMemberResponseDto(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") int id, @AuthenticationPrincipal MemberDetails memberDetails) {
        String email = memberDetails.getEmail();

        memberService.deleteMember(id, email);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
