package com.kaua.devkit.platform.application.repositories;

import com.kaua.devkit.platform.domain.auth.OAuthClient;

import java.util.Optional;

public interface OAuthClientRepository {

    Optional<OAuthClient> clientOfClientId(String clientId);
}
