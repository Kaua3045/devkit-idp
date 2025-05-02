package com.kaua.devkit.platform.domain.users;

public interface PasswordEncryption {

    String encrypt(String rawPassword);

    boolean matches(String rawPassword, String encryptedPassword);
}
