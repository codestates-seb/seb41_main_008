package com.nfteam.server.coin.service;

import com.nfteam.server.coin.entity.Coin;
import com.nfteam.server.coin.entity.CoinMemberRel;
import com.nfteam.server.coin.repository.CoinMemberRelRepository;
import com.nfteam.server.coin.repository.CoinRepository;
import com.nfteam.server.dto.request.coinRel.AddMemberCoinData;
import com.nfteam.server.dto.request.coinRel.CoinPatchRequest;
import com.nfteam.server.exception.coin.CoinMemberRelNotFoundException;
import com.nfteam.server.exception.coin.CoinNotFoundException;
import com.nfteam.server.exception.coin.ExceedCoinKindsException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CoinMemberRelService {


    private final MemberRepository memberRepository;
    private final CoinMemberRelRepository coinMemberRelRepository;

    private final CoinRepository coinRepository;


    @Transactional
    public void saveCoinData(AddMemberCoinData coinData, MemberDetails memberDetails) {

        Long coinId=(coinRepository.findByCoinName(
                coinData.getCoinName()).
                orElseThrow(()->new CoinNotFoundException(coinData.getCoinName())))
                .getCoinId();



        Coin coin=new Coin(coinId,coinData.getCoinName(),findCoinFee(coinId));

        CoinMemberRel coinMemberRel=new CoinMemberRel(coin,memberDetails,coinData.getCoinCount());
        coinMemberRelRepository.save(coinMemberRel);
    }

    private Double findCoinFee(Long coinId) {
        Coin coin=new Coin(coinId);
        return coin.getWithdrawFee();
    }


    @Transactional
    public Double update( CoinPatchRequest request, MemberDetails memberDetails) {
        System.out.println("컨트롤러 이상 무");

        CoinMemberRel coinMemberRel=coinMemberRelRepository.findByMemberAndCoinName(memberDetails.getMemberId(),getCoinByName(request.getCoinName()))
                .orElseThrow(()->new CoinMemberRelNotFoundException(memberDetails.getMemberId()));

        /*
        Long id=new Long(1);


        CoinMemberRel coinMemberRel=coinMemberRelRepository.findByMemberAndCoinName(id,getCoinByName(request.getCoinName()))
                .orElseThrow(()->new CoinMemberRelNotFoundException(memberDetails.getMemberId()));
                테스트 용*/



        //조회를 해서 해당 코인 이 있는지 없는지 여부 판단
        if(validateCoinId(coinMemberRel.getCoin().getCoinId())){
            throw new CoinNotFoundException(coinMemberRel.getCoin().getCoinId());
        }

        if(request.getIncrease()>0){
            coinMemberRel.addCoinCount(request.getIncrease());
        }
        else coinMemberRel.minusCoinCount(request.getIncrease());


        //조회 후 값에 대한 변동값을 추가
        return coinMemberRel.getCoinCount();
    }

    private Long getCoinByName(String coinName) {
        List<String> COIN = List.of("SOL", "BTC", "DOGE", "ETH", "ETC");
        for (int i=0;i<COIN.size();i++){
            if(COIN.get(i).equals(coinName)) return (long) i;
        }
        throw new CoinNotFoundException(coinName);
    }

    private boolean validateCoinId(Long coinId) {
        return coinId >= 5 || coinId == 0;
    }



    @Transactional(readOnly = true)
    //회원이 가지고 있는 코인 갯수
    public HashMap<String,Double> getMemberCoinsCountList(Long memberId) {
        List<CoinMemberRel> coinMemberRel = coinMemberRelRepository.
                findTableByMemberId(memberId);

        if (coinMemberRel.isEmpty()) {
            throw new CoinMemberRelNotFoundException(memberId);
        }

        //중복 검사
        validateCoinKinds(coinMemberRel.size());

        HashMap<String,Double> hashMap=new HashMap<>();
        for (CoinMemberRel relId:coinMemberRel){
            hashMap.put(relId.getCoin().getCoinName(),relId.getCoinCount());
        }

        return hashMap;


    }

    private void validateCoinKinds(int size) {

        if(size>5) throw new ExceedCoinKindsException();
    }




    @Transactional
    public void delete(String coinName, MemberDetails memberDetails) {
        CoinMemberRel coinMemberRel=findCoinMemberRel(memberDetails,coinName);
        coinMemberRelRepository.deleteById(coinMemberRel.getRelId());
    }
    @Transactional
    public void deleteAll(Long memberId) {

        coinMemberRelRepository.deleteAllByMember(memberId);

    }



    private CoinMemberRel findCoinMemberRel(MemberDetails memberDetails, String coinName){
        Coin coin=coinRepository.findByCoinName(coinName).orElseThrow(()->new CoinNotFoundException(coinName));
        Member member=memberRepository.findByEmail(memberDetails.getEmail()).orElseThrow(()->new MemberNotFoundException(memberDetails.getEmail()));
        return coinMemberRelRepository.findByMemberAndCoin(member,coin).orElseThrow(()->new CoinMemberRelNotFoundException(member.getEmail(),coin.getCoinName()));
    }



}
