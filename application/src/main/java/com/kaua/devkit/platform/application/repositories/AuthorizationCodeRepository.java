package com.kaua.devkit.platform.application.repositories;

import com.kaua.devkit.platform.domain.auth.code.AuthorizationCode;

import java.util.Optional;

public interface AuthorizationCodeRepository {

    Optional<AuthorizationCode> authorizationCodeOfCode(String code);

    AuthorizationCode save(AuthorizationCode authorizationCode);
}
