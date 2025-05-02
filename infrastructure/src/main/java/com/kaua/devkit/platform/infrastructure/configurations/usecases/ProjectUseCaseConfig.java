package com.kaua.devkit.platform.infrastructure.configurations.usecases;

import com.kaua.devkit.platform.application.repositories.ProjectRepository;
import com.kaua.devkit.platform.application.repositories.TeamRepository;
import com.kaua.devkit.platform.application.usecases.projects.create.CreateProjectUseCase;
import com.kaua.devkit.platform.application.usecases.projects.create.DefaultCreateProjectUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ProjectUseCaseConfig {

    @Bean
    public CreateProjectUseCase createProjectUseCase(
            final ProjectRepository projectRepository,
            final TeamRepository teamRepository
    ) {
        return new DefaultCreateProjectUseCase(projectRepository, teamRepository);
    }
}
