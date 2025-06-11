package com.kaua.devkit.platform.infrastructure.rest.controllers;

import com.kaua.devkit.platform.application.usecases.applications.create.CreateApplicationUseCase;
import com.kaua.devkit.platform.infrastructure.applications.req.CreateApplicationRequest;
import com.kaua.devkit.platform.infrastructure.applications.res.CreateApplicationResponse;
import com.kaua.devkit.platform.infrastructure.rest.ApplicationAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ApplicationRestController implements ApplicationAPI {

    private final CreateApplicationUseCase createApplicationUseCase;

    public ApplicationRestController(
            final CreateApplicationUseCase createApplicationUseCase
    ) {
        this.createApplicationUseCase = Objects.requireNonNull(createApplicationUseCase);
    }

    @Override
    public ResponseEntity<CreateApplicationResponse> createApplication(final CreateApplicationRequest request) {
        final var aOutput = this.createApplicationUseCase.execute(request.toInput());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateApplicationResponse.from(aOutput));
    }
}
