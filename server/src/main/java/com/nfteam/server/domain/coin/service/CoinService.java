package com.nfteam.server.domain.coin.service;

import com.nfteam.server.domain.coin.entity.Coin;
import com.nfteam.server.domain.coin.entity.CoinMemberRel;
import com.nfteam.server.domain.coin.entity.CoinOrder;
import com.nfteam.server.domain.coin.repository.CoinMemberRelRepository;
import com.nfteam.server.domain.coin.repository.CoinOrderRepository;
import com.nfteam.server.domain.coin.repository.CoinRepository;
import com.nfteam.server.domain.member.entity.Member;
import com.nfteam.server.domain.member.repository.MemberRepository;
import com.nfteam.server.dto.request.coin.CoinPurchaseRequest;
import com.nfteam.server.dto.response.coin.CoinPurchaseApproveResponse;
import com.nfteam.server.dto.response.coin.CoinPurchaseReadyResponse;
import com.nfteam.server.dto.response.coin.MemberCoinResponse;
import com.nfteam.server.exception.coin.CoinNotFoundException;
import com.nfteam.server.exception.coin.CoinPaymentFailedException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.security.userdetails.MemberDetails;
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
public class CoinService {

    private static final String cid = "TC0ONETIME"; // 테스트용 가맹점 코드
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

    public CoinService(MemberRepository memberRepository,
                       CoinMemberRelRepository coinMemberRelRepository,
                       CoinOrderRepository coinOrderRepository,
                       CoinRepository coinRepository,
                       RestTemplate restTemplate) {
        this.memberRepository = memberRepository;
        this.coinMemberRelRepository = coinMemberRelRepository;
        this.coinOrderRepository = coinOrderRepository;
        this.coinRepository = coinRepository;
        this.restTemplate = restTemplate;
    }

    public List<MemberCoinResponse> getMemberCoinList(Long memberId) {
        List<CoinMemberRel> memberCoinList = coinMemberRelRepository.findAllByMemberId(memberId);

        // 현재 가지고 있는 코인이 없을 경우 빈 배열 리턴
        if (memberCoinList.isEmpty()) {
            return new ArrayList<>();
        }

        List<MemberCoinResponse> responses = memberCoinList.stream()
                .map(r -> MemberCoinResponse.of(r))
                .collect(Collectors.toList());

        // 코인 번호 순 정렬
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
        // 구매자 정보 + 구매 코인 정보 + 구매 코인 갯수 + 총 가격
        Member buyer = findMember(memberDetails.getEmail());
        Coin coin = findCoin(request.getCoinName());
        Double coinCount = request.getCoinCount();
        Double totalPrice = request.getTotalPrice();

        // 구매정보 저장 - payStatus : false
        CoinOrder coinOrder = new CoinOrder(buyer, coin, coinCount, totalPrice);
        coinOrderRepository.save(coinOrder);

        // Double -> Integer 변환 (카카오 요청용)
        Double doubleCoinCount = coinCount * 1000;
        Integer intCoinCount = doubleCoinCount.intValue();
        Integer intTotalTotal = totalPrice.intValue();

        // 카카오 페이 결제 준비 요청 HttpEntity 준비
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(
                getRequestParameters(buyer.getMemberId(), coin.getCoinName(), coinOrder.getOrderId(), intCoinCount, intTotalTotal),
                getHeaders());

        // 카카오 결제 준비 요청 응답
        CoinPurchaseReadyResponse coinPurchaseReadyResponse
                = restTemplate.postForObject(readyUrl, httpEntity, CoinPurchaseReadyResponse.class);

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

    private MultiValueMap<String, Object> getRequestParameters(Long buyerId, String coinName, Long coinOrderId, Integer intCoinCount, Integer intTotalTotal) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid); // 가맹점 코드
        parameters.add("partner_order_id", String.valueOf(coinOrderId)); // 가맹점 주문번호
        parameters.add("partner_user_id", String.valueOf(buyerId)); // 가맹점 회원번호
        parameters.add("item_name", coinName); //상품명
        parameters.add("quantity", intCoinCount); // 상품수량
        parameters.add("total_amount", intTotalTotal); // 상품 총액
        parameters.add("tax_free_amount", "0"); // 상품 비과세 금액
        parameters.add("approval_url", approvalUrl); // 결제 성공 url
        parameters.add("cancel_url", cancelUrl); // 결제 취소 url
        parameters.add("fail_url", failUrl); // 결제 실패 url
        return parameters;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }

    @Transactional
    public CoinPurchaseApproveResponse approvePayment(String pgToken, String tid) {
        CoinOrder coinOrder = coinOrderRepository.findByTidWithBuyer(tid)
                .orElseThrow(() -> new CoinPaymentFailedException());

        Member buyer = coinOrder.getBuyer();
        int totalPriceIntValue = coinOrder.getTotalPrice().intValue();

        // 카카오 페이 결제 승인 요청 HttpEntity 준비
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
                getApprovalParameters(pgToken, tid, coinOrder, totalPriceIntValue),
                getHeaders());

        // 카카오 페이 결제 승인 요청 응답
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

    private static MultiValueMap<String, Object> getApprovalParameters(String pgToken, String tid, CoinOrder coinOrder, int totalPriceIntValue) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", tid);
        parameters.add("partner_order_id", String.valueOf(coinOrder.getOrderId()));
        parameters.add("partner_user_id", String.valueOf(coinOrder.getBuyer().getMemberId()));
        parameters.add("pg_token", pgToken);
        parameters.add("total_amount", totalPriceIntValue);
        return parameters;
    }

}