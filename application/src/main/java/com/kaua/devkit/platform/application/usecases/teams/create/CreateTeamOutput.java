package com.kaua.devkit.platform.application.usecases.teams.create;

import com.kaua.devkit.platform.domain.teams.Team;

public record CreateTeamOutput(
        String teamName,
        String teamId
) {

    public static CreateTeamOutput from(final Team team) {
        return new CreateTeamOutput(team.getTeamName(), team.getId().value().toString());
    }
}
