package com.kaua.devkit.platform.application.gateways;

import java.util.List;

public interface TemplateGateway {

    CreateTemplateResponse generate(CreateTemplateRequest request);

    void createTeam(CreateTeamRequest request);

    public record CreateTemplateRequest(
            String applicationName,
            String language,
            String applicationType,
            String teamName,
            String projectId
    ) {}

    public record CreateTemplateResponse(
            String repositoryUrl
    ) {}

    public record CreateTeamRequest(
            String teamName,
            List<String> maintainers
    ) {}
}
