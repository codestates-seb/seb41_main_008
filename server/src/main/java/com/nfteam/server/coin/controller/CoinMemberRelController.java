package com.nfteam.server.coin.controller;


import com.nfteam.server.coin.service.CoinMemberRelService;
import com.nfteam.server.dto.response.coin.CoinMemberRelResponse;
import com.nfteam.server.dto.response.coin.CoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/CoinMemberRel")
public class CoinMemberRelController {
    private final CoinMemberRelService coinMemberRelService;

    @GetMapping("/GetMembersCoinCount/{member-id}")
    public ResponseEntity GetMembersCoinCount(@PathVariable("member-id")Long memberId){
        CoinMemberRelResponse member= CoinMemberRelResponse.of(coinMemberRelService.findMember(memberId));
        List<CoinMemberRelResponse> coins=coinMemberRelService.getMembersCoinsCountList(memberId);

        return new ResponseEntity<>(new , HttpStatus.OK);


    }


}
