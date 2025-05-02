package com.kaua.devkit.platform.infrastructure.rest.controllers;

import com.kaua.devkit.platform.application.usecases.teams.create.CreateTeamUseCase;
import com.kaua.devkit.platform.infrastructure.rest.TeamAPI;
import com.kaua.devkit.platform.infrastructure.teams.req.CreateTeamRequest;
import com.kaua.devkit.platform.infrastructure.teams.res.CreateTeamResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class TeamRestController implements TeamAPI {

    private final CreateTeamUseCase createTeamUseCase;

    public TeamRestController(
            final CreateTeamUseCase createTeamUseCase
    ) {
        this.createTeamUseCase = Objects.requireNonNull(createTeamUseCase);
    }

    @Override
    public ResponseEntity<CreateTeamResponse> createTeam(final CreateTeamRequest request) {
        final var aOutput = this.createTeamUseCase.execute(request.toInput());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateTeamResponse.from(aOutput));
    }
}
