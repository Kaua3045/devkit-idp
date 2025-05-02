package com.kaua.devkit.platform.infrastructure.projects.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaua.devkit.platform.application.usecases.projects.create.CreateProjectInput;

public record CreateProjectRequest(
        @JsonProperty("project_name") String projectName,
        @JsonProperty("description") String description,
        @JsonProperty("team_id") String temId
) {

    public CreateProjectInput toInput() {
        return CreateProjectInput.with(projectName, description, temId);
    }
}
