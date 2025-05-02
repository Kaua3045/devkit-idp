package com.kaua.devkit.platform.domain.teams;

import com.kaua.devkit.platform.domain.UnitTest;
import com.kaua.devkit.platform.domain.users.UserID;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
import com.kaua.devkit.platform.domain.utils.InstantUtils;
import com.kaua.devkit.platform.domain.validation.handler.NotificationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TeamMemberTest extends UnitTest {

    @Test
    void givenAValidValues_whenCallNewMember_thenShouldReturnTeamMember() {
        final var aUserId = new UserID(IdentifierUtils.generateNewMonotonicULID());
        final var aRole = "TECHLEAD";

        final var aTeamMember = TeamMember.newMember(aUserId, TeamMemberRole.from(aRole).get());

        Assertions.assertNotNull(aTeamMember);
        Assertions.assertNotNull(aTeamMember.getId());
        Assertions.assertEquals(aUserId, aTeamMember.getUserId());
        Assertions.assertEquals(aRole, aTeamMember.getRole().name());
        Assertions.assertNotNull(aTeamMember.getCreatedAt());
        Assertions.assertNotNull(aTeamMember.getUpdatedAt());
        Assertions.assertDoesNotThrow(() -> aTeamMember.validate(NotificationHandler.create()));
        Assertions.assertDoesNotThrow(aTeamMember::toString);
    }

    @Test
    void givenAValidValues_whenCallWith_thenShouldReturnTeamMember() {
        final var aTeamMemberId = new TeamMemberId(IdentifierUtils.generateNewMonotonicULID());
        final var aVersion = 0L;
        final var aUserId = new UserID(IdentifierUtils.generateNewMonotonicULID());
        final var aRole = TeamMemberRole.JUNIOR;
        final var aNow = InstantUtils.now();

        final var aTeamMember = TeamMember.with(
                aTeamMemberId,
                aVersion,
                aUserId,
                aRole,
                aNow,
                aNow
        );

        Assertions.assertEquals(aTeamMemberId, aTeamMember.getId());
        Assertions.assertEquals(aVersion, aTeamMember.getVersion());
        Assertions.assertEquals(aUserId, aTeamMember.getUserId());
        Assertions.assertEquals(aRole, aTeamMember.getRole());
        Assertions.assertEquals(aNow, aTeamMember.getCreatedAt());
        Assertions.assertEquals(aNow, aTeamMember.getUpdatedAt());
    }
}
