package com.nfteam.server.coin.service;

import com.nfteam.server.batch.repository.CoinRankingRepository;
import com.nfteam.server.coin.entity.Coin;
import com.nfteam.server.coin.entity.CoinOrder;
import com.nfteam.server.coin.repository.CoinHistoryRepository;
import com.nfteam.server.coin.repository.CoinOrderRepository;
import com.nfteam.server.coin.repository.CoinRepository;
import com.nfteam.server.dto.request.coin.CoinPurchaseRequest;
import com.nfteam.server.dto.response.coin.CoinPurchaseApproveResponse;
import com.nfteam.server.exception.coin.CoinNotFoundException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import com.nfteam.server.security.userdetails.MemberDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoinPayService {

    private static final String cid = "TC0ONETIME";
    private static final String readyUrl = "https://kapi.kakao.com/v1/payment/ready";
    private static final String approveUrl = "https://kapi.kakao.com/v1/payment/approve";


    private final MemberRepository memberRepository;
    private final CoinOrderRepository coinOrderRepository;
    private final CoinRepository coinRepository;
    private final RestTemplate restTemplate;

    @Value("${common.data.kakao}")
    private String authorization;
    @Value("${common.data.success}")
    private String successUrl;

    @Value("${common.data.cancel}")
    private String cancelUrl;
    @Getter
    @Value("${common.data.fail}")
    private String failUrl;
    private final CoinRankingRepository coinRankingRepository;
    private final CoinHistoryRepository coinHistoryRepository;

    public void startPayment(CoinPurchaseRequest request, MemberDetails memberDetails) {

        // 현재 구매자 정보
        Member buyer = findMember(memberDetails.getEmail());
        // 구매 코인 정보
        Coin coin = findCoin(request.getCoinId());
        // 구매량
        Double coinCount = request.getCoinCount();

        // 구매정보 저장
        CoinOrder coinOrder = new CoinOrder(buyer, coin, coinCount);
        coinOrderRepository.save(coinOrder);

        //카카오톡 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid); // 가맹점 코드
        parameters.add("partner_order_id", String.valueOf(coinOrder.getOrderId())); // 가맹점 주문번호
        parameters.add("partner_user_id", String.valueOf(buyer.getMemberId())); // 가맹점 회원번호
        parameters.add("item_name", coin.getCoinName()); //상품명
        parameters.add("quantity", String.valueOf(request.getCoinCount())); // 상품수량
        parameters.add("total_amount", String.valueOf(request.getTotalPrice())); // 상품 총액
        parameters.add("tax_free_amount", "0"); // 상품 비과세 금액
        parameters.add("approval_url", successUrl); // 결제 성공 url
        parameters.add("cancel_url", cancelUrl); // 결제 취소 url
        parameters.add("fail_url", failUrl); // 결제 실패 url

        // 보낼 파라미터와 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 보낼 외부 url, 요청 메시지(header,parameter), 처리후 값을 받아올 클래스.
        CoinPurchaseApproveResponse coinPurchaseApproveResponse = restTemplate.postForObject(readyUrl, requestEntity, CoinPurchaseApproveResponse.class);
//
//        if (coinPurchaseApproveResponse != null) { //결재번호를 저장하면서 맴버에도 해당 정보를 저장한다.
//            order.setTid(ready.getTid());
//            orderRepository.save(order);
//            member.addOrder(order);
//            memberRepository.save(member);
//            log.info("결제번호와 결제링크를 발부 받음 그리고 해당데이터를 저장함.");
//        }
    }

    private Coin findCoin(Long coinId) {
        return coinRepository.findById(coinId)
                .orElseThrow(() -> new CoinNotFoundException(coinId));
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }

}