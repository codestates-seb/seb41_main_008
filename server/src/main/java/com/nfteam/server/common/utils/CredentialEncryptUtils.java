package com.nfteam.server.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class CredentialEncryptUtils {

    public final String alg;
    public final String algKey;
    public final String iv;

    public CredentialEncryptUtils(@Value("${credential.alg-name}") String alg,
                                  @Value("${credential.alg-key}") String algKey,
                                  @Value("${credential.iv}") String iv) {
        this.alg = alg;
        this.algKey = algKey;
        this.iv = iv;
    }

    public String encryptRecordByAES256(String record) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec secretKeySpec = new SecretKeySpec(algKey.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] encryptedRecord = cipher.doFinal(record.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedRecord);
    }

    public String decryptRecordByAES256(String record) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec secretKeySpec = new SecretKeySpec(algKey.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decodedByteRecord = Base64.getDecoder().decode(record.trim());
        byte[] decryptedRecord = cipher.doFinal(decodedByteRecord);
        return new String(decryptedRecord, "UTF-8");
    }

}
