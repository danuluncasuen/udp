package com.utm.server.service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class EncryptionService {

    private Key privateKey;

    private Key publicKey;

    private Key foreignPublicKey;

    private KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

    public EncryptionService() throws NoSuchAlgorithmException {

//        SecureRandom secureRandom = new SecureRandom();
//        keyPairGenerator.initialize(512, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();

    }

    public byte[] encrypt(String message) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, foreignPublicKey);
        byte[] bytes = cipher.doFinal(message.getBytes());
        return bytes;
    }

    public byte[] decrypt(String enc) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = enc.getBytes();
        byte decryptedBytes[] = decryptCipher.doFinal(bytes);
        return decryptedBytes;
    }


    public Key getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(Key privateKey) {
        this.privateKey = privateKey;
    }

    public Key getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(Key publicKey) {
        this.publicKey = publicKey;
    }

    public Key getForeignPublicKey() {
        return foreignPublicKey;
    }

    public void setForeignPublicKey(Key foreignPublicKey) {
        this.foreignPublicKey = foreignPublicKey;
    }
}
