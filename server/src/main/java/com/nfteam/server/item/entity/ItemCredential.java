package com.nfteam.server.item.entity;

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
    @Column(name = "encryption_value", nullable = false, length = 100)
    private String encryptionValue;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;

    protected ItemCredential() {
    }

    public void assignItem(Item item) {
        this.item = item;
    }


}
