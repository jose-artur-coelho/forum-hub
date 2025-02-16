package com.alura.forumhub.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}
