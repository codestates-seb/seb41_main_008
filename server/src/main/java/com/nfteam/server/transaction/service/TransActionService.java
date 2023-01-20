package com.nfteam.server.transaction.service;

import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.cart.repository.CartRepository;
import com.nfteam.server.coin.entity.Coin;
import com.nfteam.server.common.utils.CredentialEncryptUtils;
import com.nfteam.server.dto.request.transaction.TransActionCreateRequest;
import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;
import com.nfteam.server.exception.item.ItemCollectionNotFoundException;
import com.nfteam.server.exception.item.ItemNotFoundException;
import com.nfteam.server.exception.member.MemberNotFoundException;
import com.nfteam.server.exception.transaction.TransRecordNotValidException;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.entity.ItemCollection;
import com.nfteam.server.item.repository.ItemCollectionRepository;
import com.nfteam.server.item.repository.ItemRepository;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.repository.MemberRepository;
import com.nfteam.server.security.userdetails.MemberDetails;
import com.nfteam.server.transaction.entity.TransAction;
import com.nfteam.server.transaction.repository.TransActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransActionService {

    private final ItemRepository itemRepository;
    private final ItemCollectionRepository itemCollectionRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final TransActionRepository transActionRepository;
    private final CredentialEncryptUtils credentialEncryptUtils;

    @Transactional
    public void saveOnePurchaseRecord(TransActionCreateRequest transActionCreateRequest, MemberDetails memberDetails) throws Exception {
        Member buyer = getMemberByEmail(memberDetails.getEmail());
        TransAction transAction = makeTransAction(transActionCreateRequest, buyer);

        // 거래 기록 저장.
        transActionRepository.save(transAction);

        // 카트 거래 완료 체크.
        Cart cart = findCart(buyer);
        cart.changePaymentYn(true);

        // 현재 구매자에게 새로운 장바구니 부여
        cartRepository.save(new Cart(buyer));
    }

    @Transactional
    public void saveBulkPurchaseRecord(List<TransActionCreateRequest> requests, MemberDetails memberDetails) {
        List<TransAction> transActions = new ArrayList<>();
        Member buyer = getMemberByEmail(memberDetails.getEmail());

        requests.stream().forEach(request -> {
            try {
                transActions.add(makeTransAction(request, buyer));
            } catch (Exception e) {
                throw new NFTCustomException(ExceptionCode.TRANSACTION_FAILED, e.getMessage());
            }
        });

        // 거래 기록 저장
        transActionRepository.saveAll(transActions);

        // 카트 거래 완료 체크.
        Cart cart = findCart(buyer);
        cart.changePaymentYn(true);

        // 현재 구매자에게 새로운 장바구니 부여
        cartRepository.save(new Cart(buyer));
    }

    public TransAction makeTransAction(TransActionCreateRequest transActionCreateRequest, Member buyer) throws Exception {
        Item item = getItemByIdWithOwnerAndCredential(transActionCreateRequest.getItemId());
        ItemCollection collection = getCollectionByIdWithCoin(transActionCreateRequest.getCollectionId());

        Member seller = item.getMember();
        Coin coin = collection.getCoin();

        // 판매 가능 상품 체크
        checkSaleStatus(item.getOnSale());
        // 상품 컬렉션 정보 검증
        checkItemCollectionValidate(item.getCollection().getCollectionId(), collection.getCollectionId());
        // 판매자 검증
        validateSeller(transActionCreateRequest, item, buyer);
        // 거래 코인 검증
        validatePayment(transActionCreateRequest, item, coin);
        // 거래 기록 (Credential) 검증
        validateTransRecord(item);
        // itemCredential 거래내역 기록
        recordNewTransHistory(transActionCreateRequest, buyer, item);
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
                .transPrice(Double.parseDouble(transActionCreateRequest.getTransPrice()))
                .build();
    }

    private Item getItemByIdWithOwnerAndCredential(String itemId) {
        return itemRepository.findItemWithOwnerAndCredential(Long.parseLong(itemId))
                .orElseThrow(() -> new ItemNotFoundException(Long.parseLong(itemId)));
    }

    private ItemCollection getCollectionByIdWithCoin(String collectionId) {
        return itemCollectionRepository.findCollectionWithCoin(Long.parseLong(collectionId))
                .orElseThrow(() -> new ItemCollectionNotFoundException(Long.parseLong(collectionId)));
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    private void checkSaleStatus(Boolean onSale) {
        if (!onSale) throw new TransRecordNotValidException("현재 판매중인 상품이 아닙니다.");
    }

    private void validateSeller(TransActionCreateRequest transActionCreateRequest, Item item, Member buyer) {
        Long sellerId = Long.parseLong(transActionCreateRequest.getSellerId());
        if (sellerId != item.getMember().getMemberId()) {
            // 거래 불가 처리
            item.updateSaleStatus(false);
            throw new TransRecordNotValidException("판매자와 아이템 소유자 불일치");
        }
        if (sellerId == buyer.getMemberId()) {
            throw new TransRecordNotValidException("본인 상품을 구매할 수 없습니다.");
        }
    }

    private void validatePayment(TransActionCreateRequest transActionCreateRequest, Item item, Coin coin) {
        if (coin.getCoinId() != Long.parseLong(transActionCreateRequest.getCoinId())) {
            throw new TransRecordNotValidException("거래 수단 코인이 올바르지 않습니다.");
        }
    }

    private void checkItemCollectionValidate(Long itemCollectionId, Long requestCollectionId) {
        if (itemCollectionId != requestCollectionId) {
            throw new TransRecordNotValidException("아이템 소속 컬렉션 정보가 올바르지 않습니다.");
        }
    }

    private void validateTransRecord(Item item) throws Exception {
        String transEncryption = item.getItemCredential().getTransEncryption();
        String lastTransRecord = transEncryption.substring(transEncryption.lastIndexOf(",") + 1);

        String record = credentialEncryptUtils.decryptRecordByAES256(lastTransRecord);
        Long lastOwnerId = Long.parseLong(record.split("-")[1]);

        if (lastOwnerId != item.getMember().getMemberId()) {
            // 거래 불가 처리
            item.updateSaleStatus(false);
            throw new TransRecordNotValidException("아이템 Credential 정보와 아이템 저장 정보 불일치 (이상 기록 확인 필요)");
        }
    }

    private void recordNewTransHistory(TransActionCreateRequest transActionCreateRequest, Member buyer, Item item) throws Exception {
        StringBuilder record = new StringBuilder();
        record.append(transActionCreateRequest.getSellerId()).append("-")
                .append(buyer.getMemberId()).append("-")
                .append(transActionCreateRequest.getCoinId()).append("-")
                .append(transActionCreateRequest.getTransPrice());

        item.getItemCredential()
                .addNewTransEncryptionRecord(credentialEncryptUtils.encryptRecordByAES256(record.toString()));
    }

    private Cart findCart(Member buyer) {
        List<Cart> carts = cartRepository.findCartByMemberAndPaymentYn(buyer, false);
        if (carts.size() != 1) throw new TransRecordNotValidException("결제되지 않은 카트가 중복 존재 (기록 이상)");
        else return carts.get(0);
    }

}