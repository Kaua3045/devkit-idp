package com.kaua.devkit.platform.application.usecases.projects.create;

import com.kaua.devkit.platform.domain.projects.Project;

public record CreateProjectOutput(
        String projectName,
        String projectId
) {

    public static CreateProjectOutput from(final Project project) {
        return new CreateProjectOutput(project.getProjectName(), project.getId().value().toString());
    }
}
