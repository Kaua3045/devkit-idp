package com.kaua.devkit.platform.infrastructure.teams.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaua.devkit.platform.application.usecases.teams.create.CreateTeamInput;

public record CreateTeamRequest(
        @JsonProperty("team_name") String teamName
) {

    public CreateTeamInput toInput() {
        return CreateTeamInput.with(teamName);
    }
}
