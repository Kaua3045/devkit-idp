package com.kaua.devkit.platform.infrastructure.exceptions;

import com.kaua.devkit.platform.domain.exceptions.NoStackTraceException;

public class IdempotencyKeyUnsupportedMethodException extends NoStackTraceException {

    public IdempotencyKeyUnsupportedMethodException(final String method) {
        super("Idempotency key is not supported for this method: " + method);
    }
}
