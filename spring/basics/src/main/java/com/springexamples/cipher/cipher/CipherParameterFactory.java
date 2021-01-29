package com.springexamples.cipher.cipher;

public interface CipherParameterFactory {

    /**
     * Generates a new salt using secure random. Subsequent calls will generate always a different salt.
     *
     * @param length the length of the salt to be generated (in bytes)
     * @return the salt
     */
    byte[] newSalt(int length);

    /**
     * Generates a new initialization vector using secure random. . Subsequent calls will generate always a different
     * initialization vector.
     *
     * @param length the length of the IV to be generated (in bytes)
     * @return the IV
     */
    byte[] newIV(int length);
}
