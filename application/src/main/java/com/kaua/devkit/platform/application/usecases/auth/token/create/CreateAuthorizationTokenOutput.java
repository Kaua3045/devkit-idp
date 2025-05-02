package com.kaua.devkit.platform.application.usecases.auth.token.create;

import com.kaua.devkit.platform.application.gateways.TokenGeneratorGateway;

public record CreateAuthorizationTokenOutput(
        TokenGeneratorGateway.Token accessToken,
        TokenGeneratorGateway.Token refreshToken
) {

    public static CreateAuthorizationTokenOutput with(
            final TokenGeneratorGateway.Token accessToken,
            final TokenGeneratorGateway.Token refreshToken
    ) {
        return new CreateAuthorizationTokenOutput(accessToken, refreshToken);
    }
}
