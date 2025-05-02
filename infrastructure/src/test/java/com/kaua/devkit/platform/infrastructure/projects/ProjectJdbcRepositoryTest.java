package com.kaua.devkit.platform.infrastructure.projects;

import com.kaua.devkit.platform.AbstractRepositoryTest;
import com.kaua.devkit.platform.domain.projects.Project;
import com.kaua.devkit.platform.domain.teams.TeamId;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectJdbcRepositoryTest extends AbstractRepositoryTest {

    @Test
    void testAssertDependencies() {
        Assertions.assertNotNull(projectRepository());
    }

    @Test
    void givenAValidNewProject_whenCallSave_thenProjectIsPersisted() {
        Assertions.assertEquals(0, countProjects());

        final var aProjectName = "invoices-project";
        final var aDescription = "invoice project blablababla";
        final var aTeamId = new TeamId(IdentifierUtils.generateNewMonotonicULID());

        final var aProject = Project.newProject(
                aProjectName,
                aDescription,
                aTeamId
        );

        final var aActualProject = this.projectRepository().save(aProject);

        Assertions.assertEquals(1, countProjects());
        Assertions.assertEquals(aProject.getId(), aActualProject.getId());
        Assertions.assertEquals(aProject.getVersion(), aActualProject.getVersion());
        Assertions.assertEquals(aProject.getProjectName(), aActualProject.getProjectName());
        Assertions.assertEquals(aProject.getDescription(), aActualProject.getDescription());
        Assertions.assertEquals(aProject.getTeamId(), aActualProject.getTeamId());
        Assertions.assertEquals(aProject.getCreatedAt(), aActualProject.getCreatedAt());
        Assertions.assertEquals(aProject.getUpdatedAt(), aActualProject.getUpdatedAt());
    }

    @Test
    void givenAnNonExistsProjectName_whenCallExistsByProjectName_thenReturnFalse() {
        Assertions.assertEquals(0, countProjects());
        final var aProjectName = "invoices-project";

        final var aActualResponse = this.projectRepository().existsByProjectName(aProjectName);

        Assertions.assertFalse(aActualResponse);
    }

    @Test
    void givenAnExistsTeamName_whenCallExistsByTeamName_thenReturnTrue() {
        Assertions.assertEquals(0, countTeams());
        final var aProjectName = "invoices-project";
        final var aDescription = "invoice project blablababla";
        final var aTeamId = new TeamId(IdentifierUtils.generateNewMonotonicULID());

        final var aProject = Project.newProject(
                aProjectName,
                aDescription,
                aTeamId
        );

        this.projectRepository().save(aProject);

        Assertions.assertEquals(1, countProjects());

        final var aActualResponse = this.projectRepository().existsByProjectName(aProjectName);

        Assertions.assertTrue(aActualResponse);
    }
}
