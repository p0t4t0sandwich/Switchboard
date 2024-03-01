/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.websocket.api;

import dev.neuralnexus.switchboard.api.message.Message;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/** A class for handling encryption and decryption of messages. */
public class EncryptionHandler {
    private final int IV_LENGTH = 16;
    private static final int KEY_LENGTH = 128;
    private final SecretKey secretKey;
    private final Cipher cipher;

    public EncryptionHandler(String key) throws GeneralSecurityException {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        secretKey = new SecretKeySpec(keyBytes, "AES");
        cipher = Cipher.getInstance("AES/GCM/NoPadding");
    }

    public byte[] encrypt(Message message) throws GeneralSecurityException {
        byte[] initializationVector = new byte[IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(initializationVector);
        cipher.init(
                Cipher.ENCRYPT_MODE,
                secretKey,
                new GCMParameterSpec(IV_LENGTH * 8, initializationVector));
        byte[] encryptedData = cipher.doFinal(message.toByteArray());
        byte[] result = new byte[encryptedData.length + IV_LENGTH];
        System.arraycopy(encryptedData, 0, result, 0, encryptedData.length);
        System.arraycopy(initializationVector, 0, result, encryptedData.length, IV_LENGTH);
        return result;
    }

    public Message decrypt(byte[] cypher) throws GeneralSecurityException {
        byte[] initializationVector = new byte[IV_LENGTH];
        byte[] encryptedData = new byte[cypher.length - IV_LENGTH];
        System.arraycopy(cypher, 0, encryptedData, 0, encryptedData.length);
        System.arraycopy(cypher, encryptedData.length, initializationVector, 0, IV_LENGTH);
        cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                new GCMParameterSpec(IV_LENGTH * 8, initializationVector));
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return Message.fromByteArray(decryptedData);
    }

    public static String generateKey() {
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < KEY_LENGTH / 8; i++) {
            key.append((char) (random.nextInt(26) + 'a'));
        }
        return key.toString();
    }
}
