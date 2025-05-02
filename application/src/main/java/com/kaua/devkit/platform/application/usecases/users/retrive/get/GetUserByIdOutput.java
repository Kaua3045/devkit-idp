package com.kaua.devkit.platform.application.usecases.users.retrive.get;

import com.kaua.devkit.platform.domain.users.User;

import java.time.Instant;

public record GetUserByIdOutput(
        String id,
        String firstName,
        String lastName,
        String email,
        String role,
        Instant createdAt,
        Instant updatedAt,
        long version
) {

    public static GetUserByIdOutput from(final User aUser) {
        return new GetUserByIdOutput(
                aUser.getId().value().toString(),
                aUser.getName().firstName(),
                aUser.getName().lastName(),
                aUser.getEmail().value(),
                aUser.getRole().name(),
                aUser.getCreatedAt(),
                aUser.getUpdatedAt(),
                aUser.getVersion()
        );
    }
}
