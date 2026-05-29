package com.example.pkcn.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashMD5Utils {

    public static String hashText(String text) throws Exception {
        MessageDigest messageDigest;
        messageDigest = MessageDigest.getInstance("MD5");

        byte[] result = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
