package com.kaua.devkit.platform.application.usecases.projects.create;

import com.kaua.devkit.platform.application.exceptions.UseCaseInputCannotBeNullException;
import com.kaua.devkit.platform.application.repositories.ProjectRepository;
import com.kaua.devkit.platform.application.repositories.TeamRepository;
import com.kaua.devkit.platform.domain.exceptions.DomainException;
import com.kaua.devkit.platform.domain.exceptions.NotFoundException;
import com.kaua.devkit.platform.domain.projects.Project;
import com.kaua.devkit.platform.domain.teams.Team;
import com.kaua.devkit.platform.domain.teams.TeamId;
import com.kaua.devkit.platform.domain.utils.ULID;

import java.util.Objects;

public class DefaultCreateProjectUseCase extends CreateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;

    public DefaultCreateProjectUseCase(
            final ProjectRepository projectRepository,
            final TeamRepository teamRepository
    ) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.teamRepository = Objects.requireNonNull(teamRepository);
    }

    @Override
    public CreateProjectOutput execute(final CreateProjectInput input) {
        if (input == null) throw new UseCaseInputCannotBeNullException(CreateProjectUseCase.class);

        if (this.projectRepository.existsByProjectName(input.projectName())) {
            throw DomainException.with("Project already exists with this name"); // TODO change to custom exception
        }

        if (!this.teamRepository.existsByTeamId(input.teamId())) {
            throw NotFoundException.with(Team.class, input.teamId()).get();
        }

        final var aProject = Project.newProject(
                input.projectName(),
                input.description(),
                new TeamId(ULID.fromString(input.teamId()))
        );

        this.projectRepository.save(aProject);

        return CreateProjectOutput.from(aProject);
    }
}
