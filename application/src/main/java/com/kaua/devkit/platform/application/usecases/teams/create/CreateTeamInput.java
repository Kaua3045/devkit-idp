package com.kaua.devkit.platform.application.usecases.teams.create;

public record CreateTeamInput(
        String teamName,
        String ownerId
) {

    public static CreateTeamInput with(final String teamName, final String ownerId) {
        return new CreateTeamInput(teamName, ownerId);
    }
}
