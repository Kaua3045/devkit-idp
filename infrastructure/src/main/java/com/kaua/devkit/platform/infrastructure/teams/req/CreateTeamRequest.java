package com.kaua.devkit.platform.infrastructure.teams.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaua.devkit.platform.application.usecases.teams.create.CreateTeamInput;

public record CreateTeamRequest(
        @JsonProperty("team_name") String teamName,
        @JsonProperty("owner_id") String ownerId
) {

    public CreateTeamInput toInput() {
        return CreateTeamInput.with(teamName, ownerId);
    }
}
