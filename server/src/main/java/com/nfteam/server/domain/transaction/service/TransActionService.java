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

    @Transactional
    public CartResponse savePurchaseRecord(TransActionCreateRequest request, MemberDetails memberDetails) {
        // ????????? ?????? ??????
        List<TransActionRequestItemInfo> itemInfoList = request.getItemInfo();
        Member buyer = getMemberByEmail(memberDetails.getEmail());
        Cart buyerCart = checkAndfindCart(request.getCartId(), buyer);

        // ?????? ?????? ??????
        Long commonCoinId = checkSameCoin(itemInfoList);
        Coin coin = findCoinByCoinId(commonCoinId);

        // ?????? ?????? ??????
        List<TransAction> transActions = new ArrayList<>();
        itemInfoList.stream().forEach(item -> {
            try {
                transActions.add(makeTransAction(item, buyer, coin));
            } catch (Exception e) {
                throw new NFTCustomException(ExceptionCode.TRANSACTION_FAILED, e.getMessage());
            }
        });

        // ?????? ?????? ??????
        transActionRepository.saveAll(transActions);
        // ?????? ?????? ?????? ??????.
        buyerCart.changePaymentYn(true);
        // ?????? ??????????????? ????????? ???????????? ??????
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
            throw new TransRecordNotValidException("???????????? ?????? ????????? ?????? ?????? (?????? ??????)");
        } else if (String.valueOf(cart.getCartId()).equals(cartId)) {
            throw new TransRecordNotValidException("?????? ?????? ?????? ???????????? ?????? ?????? ?????? ????????? ????????? (?????? ??????)");
        }

        return cart;
    }

    private Long checkSameCoin(List<TransActionRequestItemInfo> itemInfoList) {
        Long commonCoinId = itemInfoList.get(0).getCoinId();
        itemInfoList.forEach(item -> {
            if (item.getCoinId() != commonCoinId) throw new TransRecordNotValidException("?????? ????????? ?????? ????????? ????????? ???????????????.");
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

        // ?????? ?????? ?????? ??????
        checkSaleStatus(item.getOnSale());
        // ????????? ??????
        validateSeller(item, seller, buyer);
        // ?????? ?????? (Credential) ??????
        validateTransRecord(item);

        // itemCredential ???????????? ??????
        recordNewTransHistory(seller, buyer, item, coin, transPrice);
        // ?????? ??????
        transferCoin(buyer, seller, item.getItemPrice(), coin);
        // item ????????? ??????
        item.assignMember(buyer);
        // item ?????? ?????? ??????
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
        if (!onSale) throw new TransRecordNotValidException("?????? ???????????? ????????? ????????????.");
    }

    private void validateSeller(Item item, Member seller, Member buyer) {
        if (seller.getMemberId() != item.getMember().getMemberId()) {
            // ?????? ?????? ??????
            item.updateSaleStatus(false);
            throw new TransRecordNotValidException("???????????? ????????? ????????? ????????? (?????? ?????? ??????)");
        }
        if (seller.getMemberId() == buyer.getMemberId()) {
            throw new TransRecordNotValidException("?????? ????????? ????????? ??? ????????????.");
        }
    }

    private void validateTransRecord(Item item) throws Exception {
        String transEncryption = item.getItemCredential().getTransEncryption();
        String lastTransRecord = transEncryption.substring(transEncryption.lastIndexOf(",") + 1);

        String record = credentialEncryptUtils.decryptRecordByAES256(lastTransRecord);
        Long lastOwnerId = Long.parseLong(record.split("-")[1]);

        if (lastOwnerId != item.getMember().getMemberId()) {
            // ?????? ?????? ??????
            item.updateSaleStatus(false);
            throw new TransRecordNotValidException("????????? Credential ????????? ????????? ?????? ?????? ????????? (?????? ?????? ?????? ??????)");
        }
    }

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
        // ?????????????????? ?????? ??????
        CoinMemberRel buyerCoinMemberRel = coinMemberRelRepository.findByMemberAndCoin(buyer, coin)
                .orElseThrow(() -> new TransRecordNotValidException("?????? ????????? ???????????????."));
        if (buyerCoinMemberRel.getCoinCount() - itemPrice < 0) throw new TransRecordNotValidException("?????? ????????? ???????????????.");
        buyerCoinMemberRel.minusCoinCount(itemPrice);

        // ??????????????? 10????????? ???????????? ????????? ?????? ??????
        CoinMemberRel sellerCoinMemberRel = coinMemberRelRepository.findByMemberAndCoin(seller, coin).orElse(new CoinMemberRel(coin, seller));
        sellerCoinMemberRel.addCoinCount(itemPrice * 0.9);

        // ???????????? ?????? ?????? ????????? ?????? ?????? ?????? Entity ??? ????????? ??????
        coinMemberRelRepository.save(sellerCoinMemberRel);
    }

}