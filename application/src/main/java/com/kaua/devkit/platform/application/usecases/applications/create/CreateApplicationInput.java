package com.kaua.devkit.platform.application.usecases.applications.create;

public record CreateApplicationInput(
        String name,
        String description,
        String projectId,
        String teamId,
        String teamName,
        String language,
        String applicationType
) {

    public static CreateApplicationInput with(
            final String aName,
            final String aDescription,
            final String aProjectId,
            final String aTeamId,
            final String aTeamName,
            final String aLanguage,
            final String aApplicationType
    ) {
        return new CreateApplicationInput(
                aName,
                aDescription,
                aProjectId,
                aTeamId,
                aTeamName,
                aLanguage,
                aApplicationType
        );
    }
}
