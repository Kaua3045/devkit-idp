package com.kaua.devkit.platform.infrastructure.users.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaua.devkit.platform.application.usecases.users.create.CreateUserOutput;

public record CreateUserResponse(
        @JsonProperty("user_id") String userId
) {

    public static CreateUserResponse from(final CreateUserOutput output) {
        return new CreateUserResponse(
                output.userId()
        );
    }
}
