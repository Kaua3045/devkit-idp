package com.kaua.devkit.platform.application.exceptions;

import com.kaua.devkit.platform.domain.exceptions.NoStackTraceException;

public class UseCaseInputCannotBeNullException extends NoStackTraceException {

    public UseCaseInputCannotBeNullException(Class<?> clazz) {
        super("Input to %s cannot be null".formatted(clazz.getSimpleName()));
    }
}
