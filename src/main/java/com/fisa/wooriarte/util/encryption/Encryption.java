package com.fisa.wooriarte.util.encryption;

import org.springframework.stereotype.Component;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Encryption {
    public String encryptionSHA256(String s) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("해당 암호화 알고리즘이 존재하지 않습니다.");
        }
        messageDigest.update(s.getBytes());
        StringBuilder sb = new StringBuilder(messageDigest.digest().length * 2);
        for(byte b : messageDigest.digest()) {
            sb.append(String.format("%02x", 0xff & b));
        }
        return sb.toString();
    }
}
