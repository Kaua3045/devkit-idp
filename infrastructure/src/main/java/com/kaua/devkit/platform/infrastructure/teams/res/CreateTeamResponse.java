package com.kaua.devkit.platform.infrastructure.teams.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaua.devkit.platform.application.usecases.teams.create.CreateTeamOutput;

public record CreateTeamResponse(
        @JsonProperty("team_id") String teamId,
        @JsonProperty("team_name") String teamName
) {

    public static CreateTeamResponse from(final CreateTeamOutput aOutput) {
        return new CreateTeamResponse(aOutput.teamId(), aOutput.teamName());
    }
}
