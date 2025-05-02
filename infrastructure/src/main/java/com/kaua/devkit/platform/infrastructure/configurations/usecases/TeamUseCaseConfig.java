package com.kaua.devkit.platform.infrastructure.configurations.usecases;

import com.kaua.devkit.platform.application.repositories.TeamRepository;
import com.kaua.devkit.platform.application.usecases.teams.create.CreateTeamUseCase;
import com.kaua.devkit.platform.application.usecases.teams.create.DefaultCreateTeamUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class TeamUseCaseConfig {

    @Bean
    public CreateTeamUseCase createTeamUseCase(final TeamRepository teamRepository) {
        return new DefaultCreateTeamUseCase(teamRepository);
    }
}
