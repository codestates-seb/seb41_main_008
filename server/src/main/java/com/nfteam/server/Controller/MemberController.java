package com.nfteam.server.Controller;

import com.nfteam.server.Domain.Member;
import com.nfteam.server.Service.MemberService;
import com.nfteam.server.dto.Request.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/join")
    private ResponseEntity<Map<String, String>> signUp(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest){
        String createMemberID=memberService.create(memberRegisterRequest);

        Map<String, String> Result=new HashMap<>();

        URI createdMember= ServletUriComponentsBuilder.fromPath("/member").path("/{id}").buildAndExpand(createMemberID).toUri();

        return ResponseEntity.created(createdMember).body(Result);
    }


    //edit 추후 수정 예정

    @GetMapping("/{userId}")
    public ResponseEntity<Member> getMember(@PathVariable("memberId") String memberID){
        return ResponseEntity.ok(memberService.findUser(memberID));
    }

    /* logout
    @DeleteMapping

     */
}
