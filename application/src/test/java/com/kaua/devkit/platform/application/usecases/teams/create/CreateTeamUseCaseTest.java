package com.kaua.devkit.platform.application.usecases.teams.create;

import com.kaua.devkit.platform.application.UseCaseTest;
import com.kaua.devkit.platform.application.exceptions.UseCaseInputCannotBeNullException;
import com.kaua.devkit.platform.application.gateways.TemplateGateway;
import com.kaua.devkit.platform.application.repositories.TeamMemberRepository;
import com.kaua.devkit.platform.application.repositories.TeamRepository;
import com.kaua.devkit.platform.application.repositories.UserRepository;
import com.kaua.devkit.platform.domain.Fixture;
import com.kaua.devkit.platform.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.argThat;

class CreateTeamUseCaseTest extends UseCaseTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TemplateGateway templateGateway;

    @InjectMocks
    private DefaultCreateTeamUseCase useCase;

    @Test
    void givenAValidValues_whenCallCreateTeamUseCase_thenReturnTeamNameAndTeamId() {
        final var aUser = Fixture.UserFixture.newUser();
        final var aTeamName = "invoices-team";
        final var aOwnerId = aUser.getId().value().toString();

        final var aInput = CreateTeamInput.with(aTeamName, aOwnerId);

        Mockito.when(teamRepository.existsByTeamName(aTeamName))
                .thenReturn(false);
        Mockito.when(userRepository.userOfId(aOwnerId))
                .thenReturn(Optional.of(aUser));
        Mockito.when(teamRepository.save(Mockito.any()))
                .thenAnswer(returnsFirstArg());
        Mockito.when(teamMemberRepository.save(Mockito.any()))
                .thenAnswer(returnsFirstArg());
        Mockito.doNothing()
                .when(templateGateway)
                .createTeam(Mockito.any());

        final var aOutput = Assertions.assertDoesNotThrow(() -> this.useCase.execute(aInput));

        Assertions.assertNotNull(aOutput);
        Assertions.assertNotNull(aOutput.teamId());
        Assertions.assertEquals(aTeamName, aOutput.teamName());

        Mockito.verify(teamRepository, Mockito.times(1)).existsByTeamName(aTeamName);
        Mockito.verify(userRepository, Mockito.times(1)).userOfId(aOwnerId);
        Mockito.verify(teamRepository, Mockito.times(1)).save(argThat(aCmd ->
                Objects.nonNull(aCmd.getId())
                        && Objects.equals(aTeamName, aCmd.getTeamName())
                        && Objects.equals(1, aCmd.getTeamMemberIds().size())
                        && Objects.nonNull(aCmd.getCreatedAt())
                        && Objects.nonNull(aCmd.getUpdatedAt())));
        Mockito.verify(teamMemberRepository, Mockito.times(1)).save(argThat(aCmd ->
                Objects.equals(aOwnerId, aCmd.getUserId().value().toString())));
        Mockito.verify(templateGateway, Mockito.times(1)).createTeam(Mockito.any());
    }

    @Test
    void givenAnInvalidExistsTeamName_whenCallCreateTeamUseCase_thenThrowsDomainException() {
        final var aTeamName = "invoices-team";
        final var aOwnerId = "1235467";

        final var expectedErrorMessage = "Team already exists with this name";

        final var aInput = CreateTeamInput.with(aTeamName, aOwnerId);

        Mockito.when(teamRepository.existsByTeamName(aTeamName))
                .thenReturn(true);

        final var aException = Assertions.assertThrows(DomainException.class,
                () -> this.useCase.execute(aInput));

        Assertions.assertEquals(expectedErrorMessage, aException.getMessage());

        Mockito.verify(teamRepository, Mockito.times(1)).existsByTeamName(aTeamName);
        Mockito.verify(userRepository, Mockito.times(0)).userOfId(Mockito.any());
        Mockito.verify(teamRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(teamMemberRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(templateGateway, Mockito.times(0)).createTeam(Mockito.any());
    }

    @Test
    void givenAnInvalidNullInput_whenCallCreateTeamUseCase_thenThrowsUseCaseInputCannotBeNullException() {
        final var expectedErrorMessage = "Input to CreateTeamUseCase cannot be null";

        final var aException = Assertions.assertThrows(UseCaseInputCannotBeNullException.class,
                () -> this.useCase.execute(null));

        Assertions.assertEquals(expectedErrorMessage, aException.getMessage());

        Mockito.verify(teamRepository, Mockito.times(0)).existsByTeamName(Mockito.any());
        Mockito.verify(userRepository, Mockito.times(0)).userOfId(Mockito.any());
        Mockito.verify(teamRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(teamMemberRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(templateGateway, Mockito.times(0)).createTeam(Mockito.any());
    }
}
