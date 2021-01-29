package com.springexamples.cipher.cipher;

import com.springexamples.cipher.dto.CipherInfoDto;
import com.springexamples.cipher.exception.EncryptionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

@Service
public class CipherServiceImpl implements CipherService {

    @Value("${cipher.encryptionKey}")
    private String encryptionKey;

    private static final int GCM_TAG_LENGTH = 128;
    private static final int GCM_IV_LENGTH = 16;

    private static final int AES_KEY_LENGTH = 256;
    private static final int AES_KEY_ITERATIONS = 5000;
    private static final int SALT_LENGTH = 32;

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final String DEFAULT_CIPHER = "AES";
    private static final String DEFAULT_CIPHERSCHEME = "AES/GCM/NoPadding";
    private static final String DEFAULT_PBKDF2_SCHEME = "PBKDF2WithHmacSHA256";

    private final CipherParameterFactory cipherParameterFactory;

    public CipherServiceImpl(
        CipherParameterFactory cipherParameterFactory) {
        this.cipherParameterFactory = cipherParameterFactory;
    }

    @Override
    public CipherInfoDto encrypt(SecretKey secretKey, byte[] payloadBytes, String aad) {
        try {
            final byte[] saltBytes = cipherParameterFactory.newSalt(SALT_LENGTH);
            final byte[] ivBytes = cipherParameterFactory.newIV(GCM_IV_LENGTH);

            SecretKey key = newAesSecretKey(secretKey.getEncoded(), saltBytes);

            byte[] cipheredBytes = encrypt(key, ivBytes, payloadBytes, aad.getBytes(DEFAULT_CHARSET));

            return CipherInfoDto.builder().cipheredBytes(cipheredBytes).initializationVector(ivBytes).salt(saltBytes).build();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new EncryptionException("Unable to perform encrypt operation [aad=" + aad + "]");
        }
    }

    @Override
    public byte[] decrypt(SecretKey secretKey, CipherInfoDto cipherInfoDto, String aad) {
        try {
            byte[] cipheredBytes = cipherInfoDto.getCipheredBytes();
            byte[] iv = cipherInfoDto.getInitializationVector();
            byte[] salt = cipherInfoDto.getSalt();

            SecretKey key = newAesSecretKey(secretKey.getEncoded(), salt);

            return decrypt(key, iv, cipheredBytes, aad.getBytes(DEFAULT_CHARSET));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new EncryptionException("Unable to perform decrypt operation [aad=" + aad + "]");
        }
    }

    @Override
    public String encryptStringWithAAD(String str, String aad) {
        byte[] strBytes = str.getBytes(DEFAULT_CHARSET);
        SecretKey secretKey = new SecretKeySpec(encryptionKey.getBytes(DEFAULT_CHARSET), "AES");
        CipherInfoDto cipherInfoDto = this.encrypt(secretKey, strBytes, aad);
        byte[] encryptedBytes = new byte[cipherInfoDto.getSalt().length + cipherInfoDto.getCipheredBytes().length + cipherInfoDto.getInitializationVector().length];

        try {
            System.arraycopy(cipherInfoDto.getSalt(), 0, encryptedBytes, 0, cipherInfoDto.getSalt().length);
            System.arraycopy(cipherInfoDto.getInitializationVector(), 0, encryptedBytes, cipherInfoDto.getSalt().length, cipherInfoDto.getInitializationVector().length);
            System.arraycopy(cipherInfoDto.getCipheredBytes(), 0, encryptedBytes, cipherInfoDto.getSalt().length + cipherInfoDto.getInitializationVector().length, cipherInfoDto.getCipheredBytes().length);
        } catch ( IndexOutOfBoundsException e) {
            throw new EncryptionException("Error copying cipher array");
        }

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decryptStringWithAAD(String encryptedStrBase64Encoded, String aad) {
        byte[] encryptedStrBytes = Base64.getDecoder().decode(encryptedStrBase64Encoded);
        SecretKey secretKey = new SecretKeySpec(encryptionKey.getBytes(DEFAULT_CHARSET), "AES");

        byte[] saltBytes;
        byte[] ivBytes;
        byte[] encryptedBytes;
        try {
            saltBytes = Arrays.copyOfRange(encryptedStrBytes, 0, SALT_LENGTH);
            ivBytes = Arrays.copyOfRange(encryptedStrBytes, SALT_LENGTH, SALT_LENGTH + GCM_IV_LENGTH);
            encryptedBytes = Arrays.copyOfRange(encryptedStrBytes, SALT_LENGTH + GCM_IV_LENGTH, encryptedStrBytes.length);
        } catch ( IndexOutOfBoundsException e) {
            throw new EncryptionException("Error copying cipher array");
        }

        CipherInfoDto cipherInfoDto = CipherInfoDto.builder().salt(saltBytes).initializationVector(ivBytes).cipheredBytes(encryptedBytes).build();
        byte[] decryptedBytes = this.decrypt(secretKey, cipherInfoDto, aad);

        return new String(decryptedBytes, DEFAULT_CHARSET);
    }


    private SecretKey newAesSecretKey(byte[] encodedKey, byte[] salt)
        throws NoSuchAlgorithmException, InvalidKeySpecException {
        String secret = new String(encodedKey, DEFAULT_CHARSET);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(DEFAULT_PBKDF2_SCHEME);
        KeySpec keyspec = new PBEKeySpec(secret.toCharArray(), salt, AES_KEY_ITERATIONS, AES_KEY_LENGTH);
        SecretKey newSecret = factory.generateSecret(keyspec);
        return new SecretKeySpec(newSecret.getEncoded(), DEFAULT_CIPHER);
    }

    private byte[] encrypt(SecretKey key, byte[] ivBytes, byte[] payloadBytes, byte[] aadBytes)
        throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return doEncryptOperation(Cipher.ENCRYPT_MODE, key, ivBytes, payloadBytes, aadBytes);
    }

    private byte[] decrypt(SecretKey key, byte[] ivBytes, byte[] payloadBytes, byte[] aadBytes)
        throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return doEncryptOperation(Cipher.DECRYPT_MODE, key, ivBytes, payloadBytes, aadBytes);
    }

    private byte[] doEncryptOperation(int encryptMode, SecretKey key, byte[] ivBytes, byte[] payloadBytes, byte[] aadBytes)
        throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        final GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, ivBytes);
        final Cipher cipher = Cipher.getInstance(DEFAULT_CIPHERSCHEME);
        cipher.init(encryptMode, key, spec);
        if (aadBytes != null && aadBytes.length > 0) {
            cipher.updateAAD(aadBytes);
        }

        return cipher.doFinal(payloadBytes);
    }

}
