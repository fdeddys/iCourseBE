package com.ddabadi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;


public class PasswordEncode {

    private static PasswordEncode passwordEncode = new PasswordEncode();

    public PasswordEncode() {

    }

    public static PasswordEncode getInstance(){
        if (passwordEncode == null){
            passwordEncode = new PasswordEncode();
        }
        return passwordEncode;
    }

    public String encode(String password){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //String encryptResult = passwordEncoder.encode(password);
        String encryptResult = passwordEncoder.encode(password);

        return encryptResult;
    }

    public String encodeWithHash(String password){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //String encryptResult = passwordEncoder.encode(password);
        String encryptResult = passwordEncoder.encode(hash(password));

        return encryptResult;
    }

    public boolean checkValid(String passInString, String password){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("passInString : "+passInString);
        return passwordEncoder.matches(passInString, password);
    }

    private String hash(String input){

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }
}
