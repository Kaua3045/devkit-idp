package com.kaua.devkit.platform.domain.teams;

import com.kaua.devkit.platform.domain.UnitTest;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
import com.kaua.devkit.platform.domain.utils.InstantUtils;
import com.kaua.devkit.platform.domain.validation.handler.NotificationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class TeamTest extends UnitTest {

    @Test
    void givenAValidValues_whenCallNewTeam_thenShouldReturnTeam() {
        final var aTeamName = "invoice-team";

        final var aTeam = Team.newTeam(aTeamName, new ArrayList<>());

        Assertions.assertNotNull(aTeam);
        Assertions.assertNotNull(aTeam.getId());
        Assertions.assertEquals(aTeamName, aTeam.getTeamName());
        Assertions.assertEquals(0, aTeam.getTeamMemberIds().size());
        Assertions.assertNotNull(aTeam.getCreatedAt());
        Assertions.assertNotNull(aTeam.getUpdatedAt());
        Assertions.assertDoesNotThrow(() -> aTeam.validate(NotificationHandler.create()));
        Assertions.assertDoesNotThrow(aTeam::toString);
    }

    @Test
    void givenAValidValues_whenCallWith_thenShouldReturnTeam() {
        final var aTeamId = new TeamId(IdentifierUtils.generateNewMonotonicULID());
        final var aVersion = 0L;
        final var aTeamName = "invoice-team";
        final var aNow = InstantUtils.now();

        final var aTeam = Team.with(
                aTeamId,
                aVersion,
                aTeamName,
                null,
                aNow,
                aNow
        );

        Assertions.assertEquals(aTeamId, aTeam.getId());
        Assertions.assertEquals(aVersion, aTeam.getVersion());
        Assertions.assertEquals(aTeamName, aTeam.getTeamName());
        Assertions.assertEquals(0, aTeam.getTeamMemberIds().size());
        Assertions.assertEquals(aNow, aTeam.getCreatedAt());
        Assertions.assertEquals(aNow, aTeam.getUpdatedAt());
    }
}
