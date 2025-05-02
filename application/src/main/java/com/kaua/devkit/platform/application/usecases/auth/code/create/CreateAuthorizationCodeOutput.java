package com.kaua.devkit.platform.application.usecases.auth.code.create;

public record CreateAuthorizationCodeOutput(
        String code,
        String redirectUri
) {
}
