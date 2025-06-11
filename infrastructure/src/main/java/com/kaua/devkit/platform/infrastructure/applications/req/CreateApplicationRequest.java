package com.kaua.devkit.platform.infrastructure.applications.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaua.devkit.platform.application.usecases.applications.create.CreateApplicationInput;

public record CreateApplicationRequest(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("project_id") String projectId,
        @JsonProperty("team_id") String teamId,
        @JsonProperty("team_name") String teamName,
        @JsonProperty("language") String language,
        @JsonProperty("application_type") String applicationType
) {

    public CreateApplicationInput toInput() {
        return new CreateApplicationInput(
                name,
                description,
                projectId,
                teamId,
                teamName,
                language,
                applicationType
        );
    }
}
