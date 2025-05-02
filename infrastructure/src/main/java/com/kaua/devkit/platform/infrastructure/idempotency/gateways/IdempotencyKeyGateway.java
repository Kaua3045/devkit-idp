package com.kaua.devkit.platform.infrastructure.idempotency.gateways;

import com.kaua.devkit.platform.infrastructure.idempotency.IdempotencyKeyDTO;
import com.kaua.devkit.platform.infrastructure.idempotency.IdempotencyKeyInput;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface IdempotencyKeyGateway {

    void save(String idempotencyKey, long ttl, TimeUnit timeUnit);

    void save(String idempotencyKey, IdempotencyKeyInput body, long ttl, TimeUnit timeUnit);

    Optional<IdempotencyKeyDTO> find(String idempotencyKey);
}
