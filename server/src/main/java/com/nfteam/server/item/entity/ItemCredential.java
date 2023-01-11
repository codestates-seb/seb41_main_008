package com.nfteam.server.item.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "item_credential")
public class ItemCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credential_id")
    private Long credentialId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 상품 고유 코드
    @Column(name = "item_code", nullable = false, length = 500)
    private String itemCode;

    // 상품 거래 내역 암호화 문자열
    @Column(name = "trans_encryption", nullable = false, length = 2000)
    private String transEncryption;

    protected ItemCredential() {
    }

    @Builder
    public ItemCredential(Long credentialId,
                          String itemCode,
                          String transEncryption) {
        this.credentialId = credentialId;
        this.itemCode = itemCode;
        this.transEncryption = transEncryption;
        this.item = item;
    }

    public void assignItem(Item item) {
        this.item = item;
    }

    private String createItemHashCode(String imgUrl) {
        // 암호화 하는 라이브러리 찾기
        return imgUrl;
    }


}
