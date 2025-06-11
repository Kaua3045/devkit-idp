package com.kaua.devkit.platform.application.usecases.applications.create;

import com.kaua.devkit.platform.application.UseCaseTest;
import com.kaua.devkit.platform.application.exceptions.UseCaseInputCannotBeNullException;
import com.kaua.devkit.platform.application.gateways.TemplateGateway;
import com.kaua.devkit.platform.application.repositories.ApplicationRepository;
import com.kaua.devkit.platform.domain.Fixture;
import com.kaua.devkit.platform.domain.exceptions.DomainException;
import com.kaua.devkit.platform.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.argThat;

class CreateApplicationUseCaseTest extends UseCaseTest {

    @Mock
    private TemplateGateway templateGateway;

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private DefaultCreateApplicationUseCase useCase;

    @Test
    void givenAValidValues_whenCallCreateApplicationUseCase_thenReturnApplicationRepositoryAndApplicationId() {
        final var aName = "invoices-app";
        final var aDescription = "invoices app blablalbla";
        final var aProject = Fixture.ProjectFixture.newProject();
        final var aProjectId = aProject.getId().value().toString();
        final var aTeamId = aProject.getTeamId().value().toString();
        final var aTeamName = "invoices-team";
        final var aLanguage = "GO";
        final var aApplicationType = "WEB";

        final var aRepositoryUrl = "http://localhost/java-project";

        final var aInput = CreateApplicationInput.with(
                aName,
                aDescription,
                aProjectId,
                aTeamId,
                aTeamName,
                aLanguage,
                aApplicationType
        );

        Mockito.when(applicationRepository.existsByProjectIdAndName(aProjectId, aName))
                .thenReturn(false);
        Mockito.when(templateGateway.generate(Mockito.any()))
                .thenReturn(new TemplateGateway.CreateTemplateResponse(aRepositoryUrl));
        Mockito.when(applicationRepository.save(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var aOutput = Assertions.assertDoesNotThrow(() -> this.useCase.execute(aInput));

        Assertions.assertNotNull(aOutput);
        Assertions.assertNotNull(aOutput.applicationId());
        Assertions.assertEquals(aRepositoryUrl, aOutput.repositoryUrl());

        Mockito.verify(applicationRepository, Mockito.times(1)).existsByProjectIdAndName(aProjectId, aName);
        Mockito.verify(templateGateway, Mockito.times(1)).generate(Mockito.any());
        Mockito.verify(applicationRepository, Mockito.times(1)).save(argThat(aCmd ->
                Objects.nonNull(aCmd.getId())
                        && Objects.equals(aName, aCmd.getName())
                        && Objects.equals(aDescription, aCmd.getDescription())
                        && Objects.equals(aProject.getId(), aCmd.getProjectId())
                        && Objects.equals(aProject.getTeamId(), aCmd.getTeamId())
                        && Objects.equals(aLanguage, aCmd.getLanguage().name())
                        && Objects.equals(aApplicationType, aCmd.getApplicationType().name())
                        && Objects.equals(false, aCmd.isDeleted())
                        && Objects.nonNull(aCmd.getCreatedAt())
                        && Objects.nonNull(aCmd.getUpdatedAt())
                        && aCmd.getDeletedAt().isEmpty()));
    }

    @Test
    void givenAnInvalidExistsNameInProjectId_whenCallCreateApplicationUseCase_thenThrowsDomainException() {
        final var aName = "invoices-app";
        final var aDescription = "invoices app blablalbla";
        final var aProject = Fixture.ProjectFixture.newProject();
        final var aProjectId = aProject.getId().value().toString();
        final var aTeamId = aProject.getTeamId().value().toString();
        final var aTeamName = "invoices-team";
        final var aLanguage = "GO";
        final var aApplicationType = "WEB";

        final var expectedErrorMessage = "Application with this name already exists in this project";

        final var aInput = CreateApplicationInput.with(
                aName,
                aDescription,
                aProjectId,
                aTeamId,
                aTeamName,
                aLanguage,
                aApplicationType
        );

        Mockito.when(applicationRepository.existsByProjectIdAndName(aProjectId, aName))
                .thenReturn(true);

        final var aException = Assertions.assertThrows(DomainException.class,
                () -> this.useCase.execute(aInput));

        Assertions.assertEquals(expectedErrorMessage, aException.getMessage());

        Mockito.verify(applicationRepository, Mockito.times(1)).existsByProjectIdAndName(aProjectId, aName);
        Mockito.verify(templateGateway, Mockito.times(0)).generate(Mockito.any());
        Mockito.verify(applicationRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    void givenAnInvalidNullInput_whenCallCreateApplicationUseCase_thenThrowsUseCaseInputCannotBeNullException() {
        final var expectedErrorMessage = "Input to CreateApplicationUseCase cannot be null";

        final var aException = Assertions.assertThrows(UseCaseInputCannotBeNullException.class,
                () -> this.useCase.execute(null));

        Assertions.assertEquals(expectedErrorMessage, aException.getMessage());

        Mockito.verify(applicationRepository, Mockito.times(0)).existsByProjectIdAndName(Mockito.any(), Mockito.any());
        Mockito.verify(templateGateway, Mockito.times(0)).generate(Mockito.any());
        Mockito.verify(applicationRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    void givenAnInvalidLanguageType_whenCallCreateApplicationUseCase_thenThrowNotFoundException() {
        final var aName = "invoices-app";
        final var aDescription = "invoices app blablalbla";
        final var aProject = Fixture.ProjectFixture.newProject();
        final var aProjectId = aProject.getId().value().toString();
        final var aTeamId = aProject.getTeamId().value().toString();
        final var aTeamName = "invoices-team";
        final var aLanguage = "non-exists";
        final var aApplicationType = "WEB";

        final var expectedErrorMessage = "Language type %s does not found".formatted(aLanguage);

        final var aInput = CreateApplicationInput.with(
                aName,
                aDescription,
                aProjectId,
                aTeamId,
                aTeamName,
                aLanguage,
                aApplicationType
        );

        Mockito.when(applicationRepository.existsByProjectIdAndName(aProjectId, aName))
                .thenReturn(false);

        final var aException = Assertions.assertThrows(NotFoundException.class,
                () -> this.useCase.execute(aInput));

        Assertions.assertEquals(expectedErrorMessage, aException.getMessage());

        Mockito.verify(applicationRepository, Mockito.times(1)).existsByProjectIdAndName(aProjectId, aName);
        Mockito.verify(templateGateway, Mockito.times(0)).generate(Mockito.any());
        Mockito.verify(applicationRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    void givenAnInvalidApplicationType_whenCallCreateApplicationUseCase_thenThrowNotFoundException() {
        final var aName = "invoices-app";
        final var aDescription = "invoices app blablalbla";
        final var aProject = Fixture.ProjectFixture.newProject();
        final var aProjectId = aProject.getId().value().toString();
        final var aTeamId = aProject.getTeamId().value().toString();
        final var aTeamName = "invoices-team";
        final var aLanguage = "JAVA";
        final var aApplicationType = "non-exists";

        final var expectedErrorMessage = "Application type %s does not found".formatted(aApplicationType);

        final var aInput = CreateApplicationInput.with(
                aName,
                aDescription,
                aProjectId,
                aTeamId,
                aTeamName,
                aLanguage,
                aApplicationType
        );

        Mockito.when(applicationRepository.existsByProjectIdAndName(aProjectId, aName))
                .thenReturn(false);

        final var aException = Assertions.assertThrows(NotFoundException.class,
                () -> this.useCase.execute(aInput));

        Assertions.assertEquals(expectedErrorMessage, aException.getMessage());

        Mockito.verify(applicationRepository, Mockito.times(1)).existsByProjectIdAndName(aProjectId, aName);
        Mockito.verify(templateGateway, Mockito.times(0)).generate(Mockito.any());
        Mockito.verify(applicationRepository, Mockito.times(0)).save(Mockito.any());
    }
}
