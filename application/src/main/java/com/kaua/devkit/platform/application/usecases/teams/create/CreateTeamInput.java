package com.kaua.devkit.platform.application.usecases.teams.create;

public record CreateTeamInput(
        String teamName
) {

    public static CreateTeamInput with(final String teamName) {
        return new CreateTeamInput(teamName);
    }
}
