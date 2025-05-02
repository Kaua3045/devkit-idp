package com.kaua.devkit.platform.application.usecases.teams.create;

import com.kaua.devkit.platform.application.exceptions.UseCaseInputCannotBeNullException;
import com.kaua.devkit.platform.application.repositories.TeamRepository;
import com.kaua.devkit.platform.domain.exceptions.DomainException;
import com.kaua.devkit.platform.domain.teams.Team;

import java.util.ArrayList;
import java.util.Objects;

public class DefaultCreateTeamUseCase extends CreateTeamUseCase {

    private final TeamRepository teamRepository;

    public DefaultCreateTeamUseCase(final TeamRepository teamRepository) {
        this.teamRepository = Objects.requireNonNull(teamRepository);
    }

    @Override
    public CreateTeamOutput execute(final CreateTeamInput input) {
        if (input == null) throw new UseCaseInputCannotBeNullException(CreateTeamUseCase.class);

        if (this.teamRepository.existsByTeamName(input.teamName())) {
            throw DomainException.with("Team already exists with this name"); // TODO change to custom exception
        }

        final var aTeam = Team.newTeam(
                input.teamName(),
                new ArrayList<>()
        );

        this.teamRepository.save(aTeam);

        return CreateTeamOutput.from(aTeam);
    }
}
