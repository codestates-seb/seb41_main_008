package com.nfteam.server.domain.item.entity;

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

    // 상품 고유 코드
    @Column(name = "item_code", nullable = false, length = 500)
    private String itemCode;

    // 상품 거래 내역 암호화 문자열
    @Column(name = "trans_encryption", nullable = false, length = 2000)
    private String transEncryption = "";

    @OneToOne(mappedBy = "itemCredential")
    private Item item;

    protected ItemCredential() {
    }

    public ItemCredential(String itemCode, String transEncryption) {
        this.itemCode = itemCode;
        this.transEncryption = transEncryption;
    }

    @Builder
    public ItemCredential(Long credentialId, Item item, String itemCode, String transEncryption) {
        this.credentialId = credentialId;
        this.item = item;
        this.itemCode = itemCode;
        this.transEncryption = transEncryption;
    }

    public void assignItem(Item item) {
        this.item = item;
    }

    public void addNewTransEncryptionRecord(String record) {
        this.transEncryption += ",";
        this.transEncryption += record;
    }

}