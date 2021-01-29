package com.springexamples.cipher.cipher;

import com.springexamples.cipher.dto.CipherInfoDto;

import javax.crypto.SecretKey;

public interface CipherService {
    /**
     * Perform an authenticated encryption function on given plain text and additional data.
     *
     * @param secretKey the secret key to be used during the encrypt operation
     * @param plaintext the plain text to encrypt. Both confidentiality and authenticity will be protected.
     * @param aad the Additional Authenticated Data. This string may be empty. This MUST be non-confidential data as
     * only its authenticity will be protected
     * @return a {@link CipherInfoDto} which encapsulates the cipher text and the initialization vector and salt used
     * for the encrypt operation
     */
    CipherInfoDto encrypt(SecretKey secretKey, byte[] plaintext, String aad);


    /**
     * Perform and authenticated decryption function from given {@link CipherInfoDto}. The AAD (Additional Authenticated
     * Data) must be provided and it has to be the same used when performing the encrypt operation.
     *
     * @param secretKey the secret key to be used during the decrypt operation
     * @param cipherInfoDto provides the cipher text together with the initialization vector and salt used on the
     * encrypt operation
     * @param aad the Additional Authenticated Data used on the encrypt operation
     * @return the cipher text decrypted, also known as the plain text
     */
    byte[] decrypt(SecretKey secretKey, CipherInfoDto cipherInfoDto, String aad);


    String encryptStringWithAAD(String str, String aad);
    String decryptStringWithAAD(String str, String aad);
}