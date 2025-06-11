package com.kaua.devkit.platform.domain.applications;

import com.kaua.devkit.platform.domain.UnitTest;
import com.kaua.devkit.platform.domain.projects.ProjectId;
import com.kaua.devkit.platform.domain.teams.TeamId;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
import com.kaua.devkit.platform.domain.utils.InstantUtils;
import com.kaua.devkit.platform.domain.validation.handler.NotificationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApplicationTest extends UnitTest {

    @Test
    void givenAValidValues_whenCallNewApplication_thenShouldReturnApplication() {
        final var aName = "invoice-application";
        final var aDescription = "invoice application blablabla";
        final var aProjectId = new ProjectId(IdentifierUtils.generateNewMonotonicULID());
        final var aRepositoryUrl = "https://localhost/invoice-application";
        final var aTeamId = new TeamId(IdentifierUtils.generateNewMonotonicULID());
        final var aLanguage = "JAVA";
        final var aApplicationType = "WEB";

        final var aApplication = Application.newApplication(
                aName,
                aDescription,
                aProjectId,
                aRepositoryUrl,
                aTeamId,
                LanguageType.from(aLanguage).get(),
                ApplicationType.from(aApplicationType).get()
        );

        Assertions.assertNotNull(aApplication);
        Assertions.assertNotNull(aApplication.getId());
        Assertions.assertEquals(aName, aApplication.getName());
        Assertions.assertEquals(aDescription, aApplication.getDescription());
        Assertions.assertEquals(aProjectId, aApplication.getProjectId());
        Assertions.assertEquals(aRepositoryUrl, aApplication.getRepositoryUrl());
        Assertions.assertEquals(aTeamId, aApplication.getTeamId());
        Assertions.assertEquals(aLanguage, aApplication.getLanguage().name());
        Assertions.assertEquals(aApplicationType, aApplication.getApplicationType().name());
        Assertions.assertFalse(aApplication.isDeleted());
        Assertions.assertNotNull(aApplication.getCreatedAt());
        Assertions.assertNotNull(aApplication.getUpdatedAt());
        Assertions.assertTrue(aApplication.getDeletedAt().isEmpty());
        Assertions.assertDoesNotThrow(() -> aApplication.validate(NotificationHandler.create()));
        Assertions.assertDoesNotThrow(aApplication::toString);
    }

    @Test
    void givenAValidValues_whenCallWith_thenShouldReturnApplication() {
        final var aApplicationId = new ApplicationId(IdentifierUtils.generateNewMonotonicULID());
        final var aVersion = 0L;
        final var aName = "invoice-application";
        final var aDescription = "invoice application blablabla";
        final var aProjectId = new ProjectId(IdentifierUtils.generateNewMonotonicULID());
        final var aRepositoryUrl = "https://localhost/invoice-application";
        final var aTeamId = new TeamId(IdentifierUtils.generateNewMonotonicULID());
        final var aLanguage = LanguageType.GO;
        final var aApplicationType = ApplicationType.JOB;
        final var aIsDeleted = false;
        final var aNow = InstantUtils.now();

        final var aApplication = Application.with(
                aApplicationId,
                aVersion,
                aName,
                aDescription,
                aProjectId,
                aRepositoryUrl,
                aTeamId,
                aLanguage,
                aApplicationType,
                aIsDeleted,
                aNow,
                aNow,
                null
        );

        Assertions.assertEquals(aApplicationId, aApplication.getId());
        Assertions.assertEquals(aVersion, aApplication.getVersion());
        Assertions.assertEquals(aName, aApplication.getName());
        Assertions.assertEquals(aDescription, aApplication.getDescription());
        Assertions.assertEquals(aProjectId, aApplication.getProjectId());
        Assertions.assertEquals(aRepositoryUrl, aApplication.getRepositoryUrl());
        Assertions.assertEquals(aTeamId, aApplication.getTeamId());
        Assertions.assertEquals(aLanguage, aApplication.getLanguage());
        Assertions.assertEquals(aApplicationType, aApplication.getApplicationType());
        Assertions.assertEquals(aIsDeleted, aApplication.isDeleted());
        Assertions.assertEquals(aNow, aApplication.getCreatedAt());
        Assertions.assertEquals(aNow, aApplication.getUpdatedAt());
        Assertions.assertTrue(aApplication.getDeletedAt().isEmpty());
    }
}
