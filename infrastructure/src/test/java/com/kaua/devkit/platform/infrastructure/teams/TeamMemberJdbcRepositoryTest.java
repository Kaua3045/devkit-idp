package com.kaua.devkit.platform.infrastructure.teams;

import com.kaua.devkit.platform.AbstractRepositoryTest;
import com.kaua.devkit.platform.domain.teams.TeamMember;
import com.kaua.devkit.platform.domain.teams.TeamMemberRole;
import com.kaua.devkit.platform.domain.users.UserID;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TeamMemberJdbcRepositoryTest extends AbstractRepositoryTest {

    @Test
    void testAssertDependencies() {
        Assertions.assertNotNull(teamMemberRepository());
    }

    @Test
    void givenAValidNewTeamMember_whenCallSave_thenTeamMemberIsPersisted() {
        Assertions.assertEquals(0, countTeamMembers());

        final var aUserId = new UserID(IdentifierUtils.generateNewMonotonicULID());
        final var aRole = TeamMemberRole.TECHLEAD;

        final var aTeamMember = TeamMember.newMember(aUserId, aRole);

        final var aActualTeamMember = this.teamMemberRepository().save(aTeamMember);

        Assertions.assertEquals(1, countTeamMembers());
        Assertions.assertEquals(aTeamMember.getId(), aActualTeamMember.getId());
        Assertions.assertEquals(aTeamMember.getVersion(), aActualTeamMember.getVersion());
        Assertions.assertEquals(aTeamMember.getUserId(), aActualTeamMember.getUserId());
        Assertions.assertEquals(aTeamMember.getRole(), aActualTeamMember.getRole());
        Assertions.assertEquals(aTeamMember.getCreatedAt(), aActualTeamMember.getCreatedAt());
        Assertions.assertEquals(aTeamMember.getUpdatedAt(), aActualTeamMember.getUpdatedAt());
    }
}
