package com.nfteam.server.dto.response.cart;

import com.nfteam.server.cart.entity.Cart;
import com.nfteam.server.dto.response.member.MemberResponseDto;
import com.nfteam.server.member.entity.Member;
import com.nfteam.server.member.entity.MemberStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartResponseDto {

    private Long cartId;

    private Boolean paymentYn = false;

    private String createdDate;

    private String modifiedDate;

    @Builder
    public CartResponseDto(Long cartId, Boolean paymentYn, LocalDateTime createdDate,
        LocalDateTime modifiedDate) {
        this.cartId = cartId;
        this.paymentYn = paymentYn;
        this.createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdDate);
        this.modifiedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(modifiedDate);
    }

    public static CartResponseDto of(Cart cart){
        return CartResponseDto.builder()
            .cartId(cart.getCartId())
            .paymentYn(cart.getPaymentYn())
            .createdDate(cart.getCreatedDate())
            .modifiedDate(cart.getModifiedDate())
            .build();
    }
}
