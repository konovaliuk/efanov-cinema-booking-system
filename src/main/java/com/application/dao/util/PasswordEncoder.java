package com.application.dao.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordEncoder {
    public static String encodePassword(String login, String passwordToEncode){
        byte[] salt = login.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt);
        byte[] hashedPassword = md.digest(passwordToEncode.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashedPassword.length; i++) {
            sb.append(Integer.toString((hashedPassword[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static void main(String[] args){
        String s1 = PasswordEncoder.encodePassword("bleach@gmail.com", "12345");
        String s2 = PasswordEncoder.encodePassword("bleach@gmail.com", "12345");
        System.out.println(s1.equals(s2));
    }
}
