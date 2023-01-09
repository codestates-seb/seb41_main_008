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

    // 해당 상품 고유 해시코드
    @Column(name = "hash_code", nullable = false, length = 100)
    private String hashCode;

    // 해당 상품 거래 내역 암호화 문자열
    @Column(name = "encryption_value", length = 100)
    private String encryptionValue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected ItemCredential() {
    }

    @Builder
    public ItemCredential(Long credentialId, String hashCode, String encryptionValue, Item item) {
        this.credentialId = credentialId;
        this.hashCode = hashCode;
        this.encryptionValue = encryptionValue;
        this.item = item;
    }

    public ItemCredential of(Item item) {
        return ItemCredential.builder()
                .hashCode(createItemHashCode(item.getItemImageUrl()))
                .item(item)
                .build();
    }

    private String createItemHashCode(String imgUrl) {
        // 암호화 하는 라이브러리 찾기
        return imgUrl;
    }


}
