package com.nfteam.server.coin.service;

import com.nfteam.server.coin.entity.Coin;
import com.nfteam.server.coin.entity.CoinMemberRel;
import com.nfteam.server.coin.entity.CoinOrder;
import com.nfteam.server.coin.repository.CoinMemberRelRepository;
import com.nfteam.server.coin.repository.CoinOrderRepository;
import com.nfteam.server.coin.repository.CoinRepository;
import com.nfteam.server.dto.request.coin.CoinPurchaseRequest;
import com.nfteam.server.dto.response.coin.CoinPurchaseApproveResponse;
import com.nfteam.server.dto.response.coin.CoinPurchaseReadyResponse;
import com.nfteam.server.dto.response.coin.MemberCoinResponse;
import com.nfteam.server.exception.coin.CoinNotFoundException;
import com.nfteam.server.exception.coin.CoinPaymentFailedException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoinService {

    private static final String cid = "TC0ONETIME";
    private static final String readyUrl = "https://kapi.kakao.com/v1/payment/ready";
    private static final String approveUrl = "https://kapi.kakao.com/v1/payment/approve";

    @Value("${pay.data.key}")
    private String authorization;
    @Value("${pay.data.approve}")
    private String approvalUrl;
    @Value("${pay.data.cancel}")
    private String cancelUrl;
    @Value("${pay.data.fail}")
    private String failUrl;

    private final MemberRepository memberRepository;
    private final CoinMemberRelRepository coinMemberRelRepository;
    private final CoinOrderRepository coinOrderRepository;
    private final CoinRepository coinRepository;
    private final RestTemplate restTemplate;
    private CoinPurchaseReadyResponse coinPurchaseReadyResponse;

    public List<MemberCoinResponse> getMemberCoinList(Long memberId) {
        List<CoinMemberRel> memberCoinList = coinMemberRelRepository.findByMemberId(memberId);

        // 현재 가지고 있는 코인이 없을 경우 빈 배열 리턴
        if (memberCoinList.isEmpty()) {
            return new ArrayList<>();
        }

        List<MemberCoinResponse> responses = memberCoinList.stream()
                .map(r -> MemberCoinResponse.of(r))
                .collect(Collectors.toList());

        responses.sort(MemberCoinResponse::compareTo);
        return responses;
    }

    public Double getCoinFee(Long coinId) {
        return coinRepository
                .findById(coinId)
                .orElseThrow(() -> new CoinNotFoundException(coinId))
                .getWithdrawFee();
    }

    @Transactional
    public CoinPurchaseReadyResponse startPayment(CoinPurchaseRequest request, MemberDetails memberDetails) {
        // 현재 구매자 정보
        Member buyer = findMember(memberDetails.getEmail());
        // 구매 코인 정보
        Coin coin = findCoin(request.getCoinName());
        // 구매량
        Double coinCount = request.getCoinCount();
        // 총 구매 가격
        Double totalPrice = request.getTotalPrice();

        // 구매정보 저장 - payStatus : false
        CoinOrder coinOrder = new CoinOrder(buyer, coin, coinCount, totalPrice);
        coinOrderRepository.save(coinOrder);

        // Double -> Integer 변환 (카카오 요청용)
        Double doubleCoinCount = coinCount * 1000;
        Integer intCoinCount = doubleCoinCount.intValue();
        Integer intTotalTotal = totalPrice.intValue();

        //카카오톡 요청
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid); // 가맹점 코드
        parameters.add("partner_order_id", String.valueOf(coinOrder.getOrderId())); // 가맹점 주문번호
        parameters.add("partner_user_id", String.valueOf(buyer.getMemberId())); // 가맹점 회원번호
        parameters.add("item_name", coin.getCoinName()); //상품명
        parameters.add("quantity", intCoinCount); // 상품수량
        parameters.add("total_amount", intTotalTotal); // 상품 총액
        parameters.add("tax_free_amount", "0"); // 상품 비과세 금액
        parameters.add("approval_url", approvalUrl); // 결제 성공 url
        parameters.add("cancel_url", cancelUrl); // 결제 취소 url
        parameters.add("fail_url", failUrl); // 결제 실패 url

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parameters, headers);

        // 카카오 결제 준비 요청 응답
        coinPurchaseReadyResponse = restTemplate.postForObject(readyUrl, httpEntity, CoinPurchaseReadyResponse.class);

        if (coinPurchaseReadyResponse != null) {
            // 코인 주문 tid 세팅
            coinOrder.updateTid(coinPurchaseReadyResponse.getTid());
        } else {
            throw new CoinPaymentFailedException();
        }

        return coinPurchaseReadyResponse;
    }

    private Coin findCoin(String coinName) {
        return coinRepository.findByCoinName(coinName)
                .orElseThrow(() -> new CoinNotFoundException(coinName));
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    @Transactional
    public CoinPurchaseApproveResponse approvePayment(String pgToken, String tid) {
        CoinOrder coinOrder = coinOrderRepository.findByTidWithBuyer(tid)
                .orElseThrow(() -> new CoinPaymentFailedException());

        Member buyer = coinOrder.getBuyer();
        int intValue = coinOrder.getTotalPrice().intValue();

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", tid);
        parameters.add("partner_order_id", String.valueOf(coinOrder.getOrderId()));
        parameters.add("partner_user_id", String.valueOf(coinOrder.getBuyer().getMemberId()));
        parameters.add("pg_token", pgToken);
        parameters.add("total_amount", intValue);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 카카오 결제 완료 응답
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, headers);

        CoinPurchaseApproveResponse coinPurchaseApproveResponse
                = restTemplate.postForObject(approveUrl, requestEntity, CoinPurchaseApproveResponse.class);

        if (coinPurchaseApproveResponse != null) {
            // 결제 상태 TRUE
            coinOrder.updatePayStatusTrue();
            // 현재 해당 회원이 해당 코인이 없을 경우 신규 관계 생성
            CoinMemberRel coinMemberRel = coinMemberRelRepository.findByMemberAndCoin(buyer, coinOrder.getCoin())
                    .orElseGet(() -> coinMemberRelRepository.save(new CoinMemberRel(coinOrder.getCoin(), buyer)));
            // 코인 갯수 업데이트
            coinMemberRel.addCoinCount(coinOrder.getCoinCount());

            return coinPurchaseApproveResponse;
        } else {
            throw new CoinPaymentFailedException();
        }
    }

}