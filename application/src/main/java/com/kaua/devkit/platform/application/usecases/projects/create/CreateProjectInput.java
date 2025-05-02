package com.kaua.devkit.platform.application.usecases.projects.create;

public record CreateProjectInput(
        String projectName,
        String description,
        String teamId
) {

    public static CreateProjectInput with(
            final String projectName,
            final String description,
            final String teamId
    ) {
        return new CreateProjectInput(projectName, description, teamId);
    }
}
