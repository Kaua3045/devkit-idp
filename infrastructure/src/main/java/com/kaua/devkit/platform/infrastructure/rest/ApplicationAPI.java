package com.kaua.devkit.platform.infrastructure.rest;

import com.kaua.devkit.platform.infrastructure.applications.req.CreateApplicationRequest;
import com.kaua.devkit.platform.infrastructure.applications.res.CreateApplicationResponse;
import com.kaua.devkit.platform.infrastructure.idempotency.IdempotencyKey;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Application", description = "Application API")
@RequestMapping("/v1/applications")
public interface ApplicationAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @IdempotencyKey
    @Operation(summary = "Create a new application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Application created successfully"),
            @ApiResponse(responseCode = "400", description = "A validation error was observed"),
            @ApiResponse(responseCode = "422", description = "A business rule was violated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<CreateApplicationResponse> createApplication(@RequestBody CreateApplicationRequest request);
}
