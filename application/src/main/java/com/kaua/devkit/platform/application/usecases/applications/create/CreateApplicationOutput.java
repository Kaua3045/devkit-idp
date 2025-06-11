package com.kaua.devkit.platform.application.usecases.applications.create;

import com.kaua.devkit.platform.domain.applications.Application;

public record CreateApplicationOutput(
        String applicationId,
        String repositoryUrl
) {

    public static CreateApplicationOutput from(final Application aApplication) {
        return new CreateApplicationOutput(
                aApplication.getId().value().toString(),
                aApplication.getRepositoryUrl()
        );
    }
}
