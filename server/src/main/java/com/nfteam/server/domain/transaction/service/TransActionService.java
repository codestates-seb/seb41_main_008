package com.nfteam.server.domain.transaction.service;

import com.nfteam.server.domain.cart.entity.Cart;
import com.nfteam.server.domain.cart.repository.CartRepository;
import com.nfteam.server.domain.coin.entity.Coin;
import com.nfteam.server.domain.coin.entity.CoinMemberRel;
import com.nfteam.server.domain.coin.repository.CoinMemberRelRepository;
import com.nfteam.server.domain.coin.repository.CoinRepository;
import com.nfteam.server.domain.item.entity.Item;
import com.nfteam.server.domain.item.entity.ItemCollection;
import com.nfteam.server.domain.item.repository.ItemRepository;
import com.nfteam.server.domain.item.utils.CredentialEncryptUtils;
import com.nfteam.server.domain.member.entity.Member;
import com.nfteam.server.domain.member.repository.MemberRepository;
import com.nfteam.server.domain.transaction.entity.TransAction;
import com.nfteam.server.domain.transaction.repository.TransActionRepository;
import com.nfteam.server.dto.request.transaction.TransActionCreateRequest;
import com.nfteam.server.dto.request.transaction.TransActionRequestItemInfo;
import com.nfteam.server.dto.response.cart.CartResponse;
import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;
import com.nfteam.server.exception.coin.CoinNotFoundException;
import com.nfteam.server.exception.item.ItemNotFoundException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.exception.transaction.TransRecordNotValidException;
import com.nfteam.server.security.userdetails.MemberDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TransActionService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CoinRepository coinRepository;
    private final TransActionRepository transActionRepository;
    private final CoinMemberRelRepository coinMemberRelRepository;

    private final CredentialEncryptUtils credentialEncryptUtils;

    public TransActionService(ItemRepository itemRepository,
                              MemberRepository memberRepository,
                              CartRepository cartRepository,
                              CoinRepository coinRepository,
                              TransActionRepository transActionRepository,
                              CoinMemberRelRepository coinMemberRelRepository,
                              CredentialEncryptUtils credentialEncryptUtils) {
        this.itemRepository = itemRepository;
        this.memberRepository = memberRepository;
        this.cartRepository = cartRepository;
        this.coinRepository = coinRepository;
        this.transActionRepository = transActionRepository;
        this.coinMemberRelRepository = coinMemberRelRepository;
        this.credentialEncryptUtils = credentialEncryptUtils;
    }

    // 거래 기록 생성
    @Transactional
    public CartResponse savePurchaseRecord(TransActionCreateRequest request, MemberDetails memberDetails) {
        // 구매자 정보 검증
        List<TransActionRequestItemInfo> itemInfoList = request.getItemInfo();
        Member buyer = getMemberByEmail(memberDetails.getEmail());
        Cart buyerCart = checkAndfindCart(request.getCartId(), buyer);

        // 코인 정보 검증
        Long commonCoinId = checkSameCoin(itemInfoList);
        Coin coin = findCoinByCoinId(commonCoinId);

        // 거래 기록 생성
        List<TransAction> transActions = new ArrayList<>();
        itemInfoList.stream().forEach(item -> {
            try {
                transActions.add(makeTransAction(item, buyer, coin));
            } catch (Exception e) {
                throw new NFTCustomException(ExceptionCode.TRANSACTION_FAILED, e.getMessage());
            }
        });

        // 거래 기록 저장
        transActionRepository.saveAll(transActions);
        // 카트 거래 완료 체크.
        buyerCart.changePaymentYn(true);
        // 현재 구매자에게 새로운 장바구니 부여
        Cart newCart = cartRepository.save(new Cart(buyer));

        return CartResponse.builder()
                .cartId(newCart.getCartId())
                .items(new ArrayList<>())
                .build();
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    private Cart checkAndfindCart(Long cartId, Member buyer) {
        List<Cart> carts = cartRepository.findCartByMemberAndPaymentYn(buyer, false);
        Cart cart = carts.get(0);

        if (carts.size() != 1) {
            throw new TransRecordNotValidException("결제되지 않은 카트가 중복 존재 (기록 이상)");
        } else if (String.valueOf(cart.getCartId()).equals(cartId)) {
            throw new TransRecordNotValidException("거래 요청 카트 아이디와 기존 멤버 카드 아이디 불일치 (기록 이상)");
        }

        return cart;
    }

    private Long checkSameCoin(List<TransActionRequestItemInfo> itemInfoList) {
        Long commonCoinId = itemInfoList.get(0).getCoinId();
        itemInfoList.forEach(item -> {
            if (item.getCoinId() != commonCoinId) throw new TransRecordNotValidException("같은 종류의 코인 상품만 거래가 가능합니다.");
        });
        return commonCoinId;
    }

    private Coin findCoinByCoinId(Long commonCoinId) {
        return coinRepository.findById(commonCoinId)
                .orElseThrow(() -> new CoinNotFoundException(commonCoinId));
    }

    public TransAction makeTransAction(TransActionRequestItemInfo itemInfo, Member buyer, Coin coin) throws Exception {
        Double transPrice = itemInfo.getTransPrice();
        Item item = getItemByIdWithOwnerAndCredentialAndCollection(itemInfo.getItemId());
        Member seller = item.getMember();
        ItemCollection collection = item.getCollection();

        // 판매 가능 상품 체크
        checkSaleStatus(item.getOnSale());
        // 판매자 검증
        validateSeller(item, seller, buyer);
        // 거래 기록 (Credential) 검증
        validateTransRecord(item);

        // itemCredential 거래내역 기록
        recordNewTransHistory(seller, buyer, item, coin, transPrice);
        // 코인 이동
        transferCoin(buyer, seller, item.getItemPrice(), coin);
        // item 소유자 변경
        item.assignMember(buyer);
        // item 판매 불가 변경
        item.updateSaleStatus(false);

        return TransAction.builder()
                .seller(seller)
                .buyer(buyer)
                .collection(collection)
                .item(item)
                .coin(coin)
                .transPrice(transPrice)
                .build();
    }

    private Item getItemByIdWithOwnerAndCredentialAndCollection(Long itemId) {
        return itemRepository.findItemWithOwnerAndCredentialAndCollection(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    private void checkSaleStatus(Boolean onSale) {
        if (!onSale) throw new TransRecordNotValidException("현재 판매중인 상품이 아닙니다.");
    }

    private void validateSeller(Item item, Member seller, Member buyer) {
        if (seller.getMemberId() != item.getMember().getMemberId()) {
            // 거래 불가 처리
            item.updateSaleStatus(false);
            throw new TransRecordNotValidException("판매자와 아이템 소유자 불일치 (이상 거래 기록)");
        }
        if (seller.getMemberId() == buyer.getMemberId()) {
            throw new TransRecordNotValidException("본인 상품을 구매할 수 없습니다.");
        }
    }

    // 거래 암호화 기록 검증
    private void validateTransRecord(Item item) throws Exception {
        String transEncryption = item.getItemCredential().getTransEncryption();
        String lastTransRecord = transEncryption.substring(transEncryption.lastIndexOf(",") + 1);

        // 마지막 거래 기록 복호화
        String record = credentialEncryptUtils.decryptRecordByAES256(lastTransRecord);
        Long lastOwnerId = Long.parseLong(record.split("-")[1]);

        if (lastOwnerId != item.getMember().getMemberId()) {
            // 거래 불가 처리
            item.updateSaleStatus(false);
            throw new TransRecordNotValidException("아이템 Credential 정보와 아이템 저장 정보 불일치 (이상 기록 확인 필요)");
        }
    }

    // 신규 거래기록 기록
    private void recordNewTransHistory(Member seller, Member buyer, Item item, Coin coin, Double transPrice) throws Exception {
        StringBuilder record = new StringBuilder();
        record.append(seller.getMemberId()).append("-")
                .append(buyer.getMemberId()).append("-")
                .append(coin.getCoinId()).append("-")
                .append(transPrice);

        item.getItemCredential().addNewTransEncryptionRecord(
                credentialEncryptUtils.encryptRecordByAES256(record.toString()));
    }

    private void transferCoin(Member buyer, Member seller, Double itemPrice, Coin coin) {
        // 구매자에게서 코인 감소
        CoinMemberRel buyerCoinMemberRel = coinMemberRelRepository.findByMemberAndCoin(buyer, coin)
                .orElseThrow(() -> new TransRecordNotValidException("구매 코인이 부족합니다."));
        if (buyerCoinMemberRel.getCoinCount() - itemPrice < 0) throw new TransRecordNotValidException("구매 코인이 부족합니다.");
        buyerCoinMemberRel.minusCoinCount(itemPrice);

        // 판매자에게 10퍼센트 수수료를 제외한 코인 송금
        CoinMemberRel sellerCoinMemberRel = coinMemberRelRepository.findByMemberAndCoin(seller, coin).orElse(new CoinMemberRel(coin, seller));
        sellerCoinMemberRel.addCoinCount(itemPrice * 0.9);

        // 판매자의 경우 해당 코인이 없을 경우 신규 Entity 가 되므로 저장
        coinMemberRelRepository.save(sellerCoinMemberRel);
    }

}