package com.kaua.devkit.platform.application.repositories;

import com.kaua.devkit.platform.domain.projects.Project;

public interface ProjectRepository {

    boolean existsByProjectName(String projectName);

    Project save(Project project);
}
