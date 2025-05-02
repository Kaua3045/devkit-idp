package com.kaua.devkit.platform.infrastructure.exceptions;

import com.kaua.devkit.platform.domain.exceptions.DomainException;

import java.util.Collections;

public class IdempotencyKeyAlreadyExistsException extends DomainException {

    public IdempotencyKeyAlreadyExistsException() {
        super("Idempotency key already exists", Collections.emptyList());
    }
}
