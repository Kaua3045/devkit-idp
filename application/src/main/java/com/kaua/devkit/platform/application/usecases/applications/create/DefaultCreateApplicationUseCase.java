package com.kaua.devkit.platform.application.usecases.applications.create;

import com.kaua.devkit.platform.application.exceptions.UseCaseInputCannotBeNullException;
import com.kaua.devkit.platform.application.gateways.TemplateGateway;
import com.kaua.devkit.platform.application.repositories.ApplicationRepository;
import com.kaua.devkit.platform.domain.applications.Application;
import com.kaua.devkit.platform.domain.applications.ApplicationType;
import com.kaua.devkit.platform.domain.applications.LanguageType;
import com.kaua.devkit.platform.domain.exceptions.DomainException;
import com.kaua.devkit.platform.domain.exceptions.NotFoundException;
import com.kaua.devkit.platform.domain.projects.ProjectId;
import com.kaua.devkit.platform.domain.teams.TeamId;
import com.kaua.devkit.platform.domain.utils.ULID;

import java.util.Objects;

public class DefaultCreateApplicationUseCase extends CreateApplicationUseCase {

    private final ApplicationRepository applicationRepository;
    private final TemplateGateway templateGateway;

    public DefaultCreateApplicationUseCase(
            final ApplicationRepository applicationRepository,
            final TemplateGateway templateGateway
    ) {
        this.applicationRepository = Objects.requireNonNull(applicationRepository);
        this.templateGateway = Objects.requireNonNull(templateGateway);
    }

    @Override
    public CreateApplicationOutput execute(final CreateApplicationInput input) {
        if (input == null) throw new UseCaseInputCannotBeNullException(CreateApplicationUseCase.class);

        // TODO check if team exists and check if project exists?
        if (this.applicationRepository.existsByProjectIdAndName(input.projectId(), input.name())) {
            throw DomainException.with("Application with this name already exists in this project");
        }

        final var aLanguage = LanguageType.from(input.language())
                .orElseThrow(() -> NotFoundException.with("Language type %s does not found".formatted(input.language())));

        final var aApplicationType = ApplicationType.from(input.applicationType())
                .orElseThrow(() -> NotFoundException.with("Application type %s does not found".formatted(input.applicationType())));

        final var aTemplate = this.templateGateway.generate(new TemplateGateway.CreateTemplateRequest(
                input.name(),
                aLanguage.name(),
                aApplicationType.name(),
                input.teamName(),
                input.projectId()
        ));

        final var aApplication = Application.newApplication(
                input.name(),
                input.description(),
                new ProjectId(ULID.fromString(input.projectId())),
                aTemplate.repositoryUrl(),
                new TeamId(ULID.fromString(input.teamId())),
                aLanguage,
                aApplicationType
        );

        this.applicationRepository.save(aApplication);

        return CreateApplicationOutput.from(aApplication);
    }
}
