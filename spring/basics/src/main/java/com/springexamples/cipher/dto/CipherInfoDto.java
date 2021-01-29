package com.springexamples.cipher.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class CipherInfoDto {

    private final byte[] salt;
    private final byte[] initializationVector;
    private final byte[] cipheredBytes;

}
