package com.kaua.devkit.platform.infrastructure.applications.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaua.devkit.platform.application.usecases.applications.create.CreateApplicationOutput;

public record CreateApplicationResponse(
        @JsonProperty("application_id") String applicationId,
        @JsonProperty("repository_url") String repositoryUrl
) {

    public static CreateApplicationResponse from(final CreateApplicationOutput aOutput) {
        return new CreateApplicationResponse(
                aOutput.applicationId(),
                aOutput.repositoryUrl()
        );
    }
}
