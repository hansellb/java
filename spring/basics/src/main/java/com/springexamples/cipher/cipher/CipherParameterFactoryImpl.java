package com.springexamples.cipher.cipher;

import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.UUID;

@Service
public class CipherParameterFactoryImpl implements CipherParameterFactory {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final String DEFAULT_FIXED_SALT = "CustomerContactsMGAPI";
    private static final byte[] FIXED_SALT = DEFAULT_FIXED_SALT.getBytes(DEFAULT_CHARSET);
    private static final int FIXED_SALT_SIZE = Math.min(8, FIXED_SALT.length);

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    static {
        SECURE_RANDOM.setSeed(UUID.randomUUID().toString().getBytes(DEFAULT_CHARSET));
        SECURE_RANDOM.setSeed(System.currentTimeMillis());
    }

    /**
     * {@inheritDoc}
     *
     * This implementation generates a new salt from both a fixed and a random part.
     */
    @Override
    public byte[] newSalt(int length) {
        byte[] salt = new byte[length];
        SECURE_RANDOM.nextBytes(salt);

        if (FIXED_SALT_SIZE >= 0) {
            System.arraycopy(FIXED_SALT, 0, salt, 0, FIXED_SALT_SIZE);
        }

        return salt;
    }

    @Override
    public byte[] newIV(int length) {
        byte[] iv = new byte[length];
        SECURE_RANDOM.nextBytes(iv);

        return iv;
    }

    String getDefaultFixedSalt() {
        return DEFAULT_FIXED_SALT.substring(0, FIXED_SALT_SIZE);
    }

}
