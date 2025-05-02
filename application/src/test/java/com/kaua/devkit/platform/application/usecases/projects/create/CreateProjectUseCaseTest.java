package com.kaua.devkit.platform.application.usecases.projects.create;

import com.kaua.devkit.platform.application.UseCaseTest;
import com.kaua.devkit.platform.application.exceptions.UseCaseInputCannotBeNullException;
import com.kaua.devkit.platform.application.repositories.ProjectRepository;
import com.kaua.devkit.platform.application.repositories.TeamRepository;
import com.kaua.devkit.platform.domain.Fixture;
import com.kaua.devkit.platform.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.argThat;

class CreateProjectUseCaseTest extends UseCaseTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private DefaultCreateProjectUseCase useCase;

    @Test
    void givenAValidValues_whenCallCreateProjectUseCase_thenReturnProjectNameAndProjectId() {
        final var aProjectName = "invoices-project";
        final var aDescription = "invoices project blablalbla";
        final var aTeam = Fixture.TeamFixture.newTeam();
        final var aTeamId = aTeam.getId().value().toString();

        final var aInput = CreateProjectInput.with(
                aProjectName,
                aDescription,
                aTeamId
        );

        Mockito.when(projectRepository.existsByProjectName(aProjectName))
                .thenReturn(false);
        Mockito.when(teamRepository.existsByTeamId(aTeamId))
                .thenReturn(true);
        Mockito.when(projectRepository.save(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var aOutput = Assertions.assertDoesNotThrow(() -> this.useCase.execute(aInput));

        Assertions.assertNotNull(aOutput);
        Assertions.assertNotNull(aOutput.projectId());
        Assertions.assertEquals(aProjectName, aOutput.projectName());

        Mockito.verify(projectRepository, Mockito.times(1)).existsByProjectName(aProjectName);
        Mockito.verify(teamRepository, Mockito.times(1)).existsByTeamId(aTeamId);
        Mockito.verify(projectRepository, Mockito.times(1)).save(argThat(aCmd ->
                Objects.nonNull(aCmd.getId())
                        && Objects.equals(aProjectName, aCmd.getProjectName())
                        && Objects.equals(aDescription, aCmd.getDescription())
                        && Objects.equals(aTeam.getId(), aCmd.getTeamId())
                        && Objects.nonNull(aCmd.getCreatedAt())
                        && Objects.nonNull(aCmd.getUpdatedAt())));
    }

    @Test
    void givenAnInvalidExistsProjectName_whenCallCreateProjectUseCase_thenThrowsDomainException() {
        final var aProjectName = "invoices-project";
        final var aDescription = "invoices project blablalbla";
        final var aTeam = Fixture.TeamFixture.newTeam();
        final var aTeamId = aTeam.getId().value().toString();

        final var expectedErrorMessage = "Project already exists with this name";

        final var aInput = CreateProjectInput.with(
                aProjectName,
                aDescription,
                aTeamId
        );

        Mockito.when(projectRepository.existsByProjectName(aProjectName))
                .thenReturn(true);

        final var aException = Assertions.assertThrows(DomainException.class,
                () -> this.useCase.execute(aInput));

        Assertions.assertEquals(expectedErrorMessage, aException.getMessage());

        Mockito.verify(projectRepository, Mockito.times(1)).existsByProjectName(aProjectName);
        Mockito.verify(teamRepository, Mockito.times(0)).existsByTeamId(Mockito.any());
        Mockito.verify(projectRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    void givenAnInvalidNullInput_whenCallCreateProjectUseCase_thenThrowsUseCaseInputCannotBeNullException() {
        final var expectedErrorMessage = "Input to CreateProjectUseCase cannot be null";

        final var aException = Assertions.assertThrows(UseCaseInputCannotBeNullException.class,
                () -> this.useCase.execute(null));

        Assertions.assertEquals(expectedErrorMessage, aException.getMessage());

        Mockito.verify(projectRepository, Mockito.times(0)).existsByProjectName(Mockito.any());
        Mockito.verify(teamRepository, Mockito.times(0)).existsByTeamId(Mockito.any());
        Mockito.verify(projectRepository, Mockito.times(0)).save(Mockito.any());
    }
}
