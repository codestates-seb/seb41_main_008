package com.nfteam.server.coin.service;

import com.nfteam.server.coin.entity.CoinMemberRel;
import com.nfteam.server.coin.repository.CoinMemberRelRepository;
import com.nfteam.server.dto.response.coin.CoinMemberRelResponse;
import com.nfteam.server.dto.response.coin.CoinResponse;
import com.nfteam.server.exception.Coin.CoinMemberRelNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinMemberRelService {
    private CoinMemberRelRepository coinMemberRelRepository;
    public List<CoinMemberRelResponse> getMembersCoinsCountList(Long memberId){
        List<CoinMemberRelResponse> memberCoins=coinMemberRelRepository.

    };



    public CoinMemberRel findMember(Long memberid){
        CoinMemberRel coinMemberRel=coinMemberRelRepository.findByMember(memberid)
                .orElseThrow(()->new CoinMemberRelNotFoundException(memberid));
        return coinMemberRel;

    };
}
