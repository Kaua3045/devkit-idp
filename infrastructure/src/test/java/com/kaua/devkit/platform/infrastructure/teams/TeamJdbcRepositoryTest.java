package com.kaua.devkit.platform.infrastructure.teams;

import com.kaua.devkit.platform.AbstractRepositoryTest;
import com.kaua.devkit.platform.domain.teams.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class TeamJdbcRepositoryTest extends AbstractRepositoryTest {

    @Test
    void testAssertDependencies() {
        Assertions.assertNotNull(teamRepository());
    }

    @Test
    void givenAValidNewTeam_whenCallSave_thenTeamIsPersisted() {
        Assertions.assertEquals(0, countTeams());

        final var aTeamName = "invoice-team";

        final var aTeam = Team.newTeam(aTeamName, new ArrayList<>());

        final var aActualTeam = this.teamRepository().save(aTeam);

        Assertions.assertEquals(1, countTeams());
        Assertions.assertEquals(aTeam.getId(), aActualTeam.getId());
        Assertions.assertEquals(aTeam.getVersion(), aActualTeam.getVersion());
        Assertions.assertEquals(aTeam.getTeamName(), aActualTeam.getTeamName());
        Assertions.assertEquals(aTeam.getTeamMemberIds().size(), aActualTeam.getTeamMemberIds().size());
        Assertions.assertEquals(aTeam.getCreatedAt(), aActualTeam.getCreatedAt());
        Assertions.assertEquals(aTeam.getUpdatedAt(), aActualTeam.getUpdatedAt());
    }

    @Test
    void givenAnNonExistsTeamName_whenCallExistsByTeamName_thenReturnFalse() {
        Assertions.assertEquals(0, countTeams());
        final var aTeamName = "invoice-team";

        final var aActualResponse = this.teamRepository().existsByTeamName(aTeamName);

        Assertions.assertFalse(aActualResponse);
    }

    @Test
    void givenAnExistsTeamName_whenCallExistsByTeamName_thenReturnTrue() {
        Assertions.assertEquals(0, countTeams());
        final var aTeamName = "invoice-team";

        final var aTeam = Team.newTeam(aTeamName, new ArrayList<>());

        this.teamRepository().save(aTeam);

        Assertions.assertEquals(1, countTeams());

        final var aActualResponse = this.teamRepository().existsByTeamName(aTeamName);

        Assertions.assertTrue(aActualResponse);
    }

    @Test
    void givenAnNonExistsId_whenCallExistsByTeamId_thenReturnFalse() {
        Assertions.assertEquals(0, countTeams());
        final var aTeamId = "12346";

        final var aActualResponse = this.teamRepository().existsByTeamId(aTeamId);

        Assertions.assertFalse(aActualResponse);
    }

    @Test
    void givenAnExistsTeamId_whenCallExistsByTeamId_thenReturnTrue() {
        Assertions.assertEquals(0, countTeams());
        final var aTeam = Team.newTeam("invoice-team", new ArrayList<>());

        this.teamRepository().save(aTeam);

        Assertions.assertEquals(1, countTeams());

        final var aActualResponse = this.teamRepository().existsByTeamId(aTeam.getId().value().toString());

        Assertions.assertTrue(aActualResponse);
    }
}
