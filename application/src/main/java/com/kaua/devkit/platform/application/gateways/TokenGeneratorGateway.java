package com.kaua.devkit.platform.application.gateways;

import com.kaua.devkit.platform.domain.auth.token.AuthorizationTokenType;

import java.time.Instant;

public interface TokenGeneratorGateway {

    Token generateToken(TokenInput input);

    public record Token(
            String tokenValue,
            String tokenJTI,
            AuthorizationTokenType type,
            String clientId,
            String sub,
            Instant expiresIn,
            Instant issuedAt
    ) {
    }

    public record TokenInput(
            String clientId,
            String sub,
            AuthorizationTokenType type
    ) {
    }
}
