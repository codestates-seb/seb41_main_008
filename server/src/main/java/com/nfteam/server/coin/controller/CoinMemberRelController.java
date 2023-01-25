package com.nfteam.server.coin.controller;


import com.nfteam.server.coin.service.CoinMemberRelService;
import com.nfteam.server.dto.request.coinRel.AddMemberCoinData;
import com.nfteam.server.dto.request.coinRel.CoinPatchRequest;
import com.nfteam.server.dto.response.coin.CoinCountResponse;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/coin")
public class CoinMemberRelController {
    private final CoinMemberRelService coinMemberRelService;


    //회원에 코인 정보 추가
    @PostMapping("/save")
    public ResponseEntity CreateCoinData(@RequestBody AddMemberCoinData coinData,
                                         @AuthenticationPrincipal MemberDetails memberDetails){
        coinMemberRelService.saveCoinData(coinData,memberDetails);

        return new ResponseEntity(HttpStatus.OK);
    }


    //멤버가 갖고 있는 코인들 정보(coin, coinCount)-> 회원 id로 찾음

    @GetMapping("/{memberId}")
    public ResponseEntity GetMembersCoinCount(@PathVariable("memberId")Long memberId){

        return new ResponseEntity<>(coinMemberRelService.getMemberCoinsCountList(memberId), HttpStatus.OK);

    }


    @PatchMapping("")
    public ResponseEntity update(
                                 @RequestBody CoinPatchRequest request,
                                 @AuthenticationPrincipal MemberDetails memberDetails
                                 ){
        Double updatedCoinCount=coinMemberRelService.update(request, memberDetails);

        return new ResponseEntity<>(new CoinCountResponse(request.getCoinName(), updatedCoinCount),HttpStatus.CREATED);
    }


    @DeleteMapping("/{coinName}")
    public ResponseEntity delete(@PathVariable("coinName") String coinName,
                                 @AuthenticationPrincipal MemberDetails memberDetails){
        coinMemberRelService.delete(coinName, memberDetails);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping()
    public ResponseEntity deleteAllCoins(@AuthenticationPrincipal MemberDetails memberDetails){
        coinMemberRelService.deleteAll(memberDetails.getMemberId());
        //coinMemberRelService.deleteAll(new Long(1));테스트 용
        return ResponseEntity.noContent().build();
    }






}
