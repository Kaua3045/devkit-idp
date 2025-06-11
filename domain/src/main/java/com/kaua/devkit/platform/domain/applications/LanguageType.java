package com.kaua.devkit.platform.domain.applications;

import java.util.Arrays;
import java.util.Optional;

public enum LanguageType {

    GO,
    JAVA;

    public static Optional<LanguageType> from(final String value) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(value))
                .findFirst();
    }
}
