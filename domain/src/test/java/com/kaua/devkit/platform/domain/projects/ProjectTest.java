package com.kaua.devkit.platform.domain.projects;

import com.kaua.devkit.platform.domain.UnitTest;
import com.kaua.devkit.platform.domain.teams.TeamId;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
import com.kaua.devkit.platform.domain.utils.InstantUtils;
import com.kaua.devkit.platform.domain.validation.handler.NotificationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectTest extends UnitTest {

    @Test
    void givenAValidValues_whenCallNewProject_thenShouldReturnProject() {
        final var aProjectName = "invoice-projects";
        final var aDescription = "invoice project blablabla";
        final var aTeamId = new TeamId(IdentifierUtils.generateNewMonotonicULID());

        final var aProject = Project.newProject(
                aProjectName,
                aDescription,
                aTeamId
        );

        Assertions.assertNotNull(aProject);
        Assertions.assertNotNull(aProject.getId());
        Assertions.assertEquals(aProjectName, aProject.getProjectName());
        Assertions.assertEquals(aDescription, aProject.getDescription());
        Assertions.assertEquals(aTeamId, aProject.getTeamId());
        Assertions.assertNotNull(aProject.getCreatedAt());
        Assertions.assertNotNull(aProject.getUpdatedAt());
        Assertions.assertDoesNotThrow(() -> aProject.validate(NotificationHandler.create()));
        Assertions.assertDoesNotThrow(aProject::toString);
    }

    @Test
    void givenAValidValues_whenCallWith_thenShouldReturnProject() {
        final var aProjectId = new ProjectId(IdentifierUtils.generateNewMonotonicULID());
        final var aVersion = 0L;
        final var aProjectName = "invoice-projects";
        final var aDescription = "invoice project blablabla";
        final var aTeamId = new TeamId(IdentifierUtils.generateNewMonotonicULID());
        final var aNow = InstantUtils.now();

        final var aProject = Project.with(
                aProjectId,
                aVersion,
                aProjectName,
                aDescription,
                aTeamId,
                aNow,
                aNow
        );

        Assertions.assertEquals(aProjectId, aProject.getId());
        Assertions.assertEquals(aVersion, aProject.getVersion());
        Assertions.assertEquals(aProjectName, aProject.getProjectName());
        Assertions.assertEquals(aDescription, aProject.getDescription());
        Assertions.assertEquals(aTeamId, aProject.getTeamId());
        Assertions.assertEquals(aNow, aProject.getCreatedAt());
        Assertions.assertEquals(aNow, aProject.getUpdatedAt());
    }
}
