package com.kaua.devkit.platform.infrastructure.rest.controllers;

import com.kaua.devkit.platform.application.usecases.projects.create.CreateProjectUseCase;
import com.kaua.devkit.platform.infrastructure.projects.req.CreateProjectRequest;
import com.kaua.devkit.platform.infrastructure.projects.res.CreateProjectResponse;
import com.kaua.devkit.platform.infrastructure.rest.ProjectAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ProjectRestController implements ProjectAPI {

    private final CreateProjectUseCase createProjectUseCase;

    public ProjectRestController(
            final CreateProjectUseCase createProjectUseCase
    ) {
        this.createProjectUseCase = Objects.requireNonNull(createProjectUseCase);
    }

    @Override
    public ResponseEntity<CreateProjectResponse> createProject(final CreateProjectRequest request) {
        final var aOutput = this.createProjectUseCase.execute(request.toInput());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateProjectResponse.from(aOutput));
    }
}
