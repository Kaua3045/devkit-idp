package com.kaua.devkit.platform.infrastructure.projects.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaua.devkit.platform.application.usecases.projects.create.CreateProjectOutput;

public record CreateProjectResponse(
        @JsonProperty("project_id") String projectId,
        @JsonProperty("project_name") String projectName
) {

    public static CreateProjectResponse from(final CreateProjectOutput aOutput) {
        return new CreateProjectResponse(aOutput.projectId(), aOutput.projectName());
    }
}
