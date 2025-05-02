package com.kaua.devkit.platform.application.usecases.users.create;

import com.kaua.devkit.platform.domain.users.User;

public record CreateUserOutput(
        String userId
) {

    public static CreateUserOutput from(final User aUser) {
        return new CreateUserOutput(
                aUser.getId().value().toString()
        );
    }
}
