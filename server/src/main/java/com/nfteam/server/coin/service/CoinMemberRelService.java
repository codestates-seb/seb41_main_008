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
    private static final List<String> COIN = List.of("SOL", "BTC", "DOGE", "ETH", "ETC");
    private CoinMemberRelRepository coinMemberRelRepository;
    //회원이 가지고 있는 코인 갯수
    public List<Double> getMembersCoinsCountList(Long memberId){
        List<Double> memberCoins=coinMemberRelRepository.findCoinCountByMember(memberId);
        return memberCoins;

    };



    public CoinMemberRel findMember(Long memberid){
        CoinMemberRel coinMemberRel=coinMemberRelRepository.findByMember(memberid)
                .orElseThrow(()->new CoinMemberRelNotFoundException(memberid));
        return coinMemberRel;

    };
}
