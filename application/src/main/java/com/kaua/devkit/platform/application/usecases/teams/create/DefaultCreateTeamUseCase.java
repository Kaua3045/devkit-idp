package com.kaua.devkit.platform.application.usecases.teams.create;

import com.kaua.devkit.platform.application.exceptions.UseCaseInputCannotBeNullException;
import com.kaua.devkit.platform.application.gateways.TemplateGateway;
import com.kaua.devkit.platform.application.repositories.TeamMemberRepository;
import com.kaua.devkit.platform.application.repositories.TeamRepository;
import com.kaua.devkit.platform.application.repositories.UserRepository;
import com.kaua.devkit.platform.domain.exceptions.DomainException;
import com.kaua.devkit.platform.domain.exceptions.NotFoundException;
import com.kaua.devkit.platform.domain.teams.Team;
import com.kaua.devkit.platform.domain.teams.TeamMember;
import com.kaua.devkit.platform.domain.teams.TeamMemberRole;
import com.kaua.devkit.platform.domain.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class DefaultCreateTeamUseCase extends CreateTeamUseCase {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;
    private final TemplateGateway templateGateway;

    public DefaultCreateTeamUseCase(
            final TeamRepository teamRepository,
            final TeamMemberRepository teamMemberRepository,
            final UserRepository userRepository,
            final TemplateGateway templateGateway
    ) {
        this.teamRepository = Objects.requireNonNull(teamRepository);
        this.teamMemberRepository = Objects.requireNonNull(teamMemberRepository);
        this.userRepository = Objects.requireNonNull(userRepository);
        this.templateGateway = Objects.requireNonNull(templateGateway);
    }

    @Override
    public CreateTeamOutput execute(final CreateTeamInput input) {
        if (input == null) throw new UseCaseInputCannotBeNullException(CreateTeamUseCase.class);

        if (this.teamRepository.existsByTeamName(input.teamName())) {
            throw DomainException.with("Team already exists with this name"); // TODO change to custom exception
        }

        final var aUser = this.userRepository.userOfId(input.ownerId())
                .orElseThrow(NotFoundException.with(User.class, input.ownerId()));

        final var aTeamMember = TeamMember.newMember(
                aUser.getId(),
                TeamMemberRole.TECHLEAD
        );

        final var aTeam = Team.newTeam(
                input.teamName(),
                new ArrayList<>(Collections.singleton(aTeamMember.getId()))
        );

        this.teamRepository.save(aTeam);
        this.teamMemberRepository.save(aTeamMember);

        // TODO in future get github name
        this.templateGateway.createTeam(new TemplateGateway.CreateTeamRequest(
                input.teamName(),
                Collections.singletonList(aUser.getName().firstName())
        ));

        return CreateTeamOutput.from(aTeam);
    }
}
