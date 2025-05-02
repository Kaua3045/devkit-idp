package com.kaua.devkit.platform.domain.teams;

import java.util.Arrays;
import java.util.Optional;

public enum TeamMemberRole {

    TECHLEAD,
    SENIOR,
    PLENO,
    JUNIOR;

    public static Optional<TeamMemberRole> from(final String value) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(value))
                .findFirst();
    }
}
