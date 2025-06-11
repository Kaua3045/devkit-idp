package com.kaua.devkit.platform.infrastructure.applications;

import com.kaua.devkit.platform.AbstractRepositoryTest;
import com.kaua.devkit.platform.domain.Fixture;
import com.kaua.devkit.platform.domain.applications.Application;
import com.kaua.devkit.platform.domain.applications.ApplicationType;
import com.kaua.devkit.platform.domain.applications.LanguageType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApplicationJdbcRepositoryTest extends AbstractRepositoryTest {

    @Test
    void testAssertDependencies() {
        Assertions.assertNotNull(applicationRepository());
    }

    @Test
    void givenAValidNewApplication_whenCallSave_thenApplicationIsPersisted() {
        Assertions.assertEquals(0, countApplications());

        final var aName = "invoices-app";
        final var aDescription = "invoices app blablalbla";
        final var aProject = Fixture.ProjectFixture.newProject();
        final var aProjectId = aProject.getId();
        final var aRepositoryUrl = "http://localhost/java-template";
        final var aTeamId = aProject.getTeamId();
        final var aLanguage = LanguageType.JAVA;
        final var aApplicationType = ApplicationType.JOB;

        final var aApplication = Application.newApplication(
                aName,
                aDescription,
                aProjectId,
                aRepositoryUrl,
                aTeamId,
                aLanguage,
                aApplicationType
        );

        final var aActualApplication = this.applicationRepository().save(aApplication);

        Assertions.assertEquals(1, countApplications());
        Assertions.assertEquals(aApplication.getId(), aActualApplication.getId());
        Assertions.assertEquals(aApplication.getVersion(), aActualApplication.getVersion());
        Assertions.assertEquals(aApplication.getName(), aActualApplication.getName());
        Assertions.assertEquals(aApplication.getDescription(), aActualApplication.getDescription());
        Assertions.assertEquals(aApplication.getProjectId(), aActualApplication.getProjectId());
        Assertions.assertEquals(aApplication.getRepositoryUrl(), aActualApplication.getRepositoryUrl());
        Assertions.assertEquals(aApplication.getTeamId(), aActualApplication.getTeamId());
        Assertions.assertEquals(aApplication.getLanguage(), aActualApplication.getLanguage());
        Assertions.assertEquals(aApplication.getApplicationType(), aActualApplication.getApplicationType());
        Assertions.assertEquals(aApplication.isDeleted(), aActualApplication.isDeleted());
        Assertions.assertEquals(aApplication.getCreatedAt(), aActualApplication.getCreatedAt());
        Assertions.assertEquals(aApplication.getUpdatedAt(), aActualApplication.getUpdatedAt());
        Assertions.assertTrue(aActualApplication.getDeletedAt().isEmpty());
    }

    @Test
    void givenAnNonExistsProjectIdAndName_whenCallExistsByProjectIdAndName_thenReturnFalse() {
        Assertions.assertEquals(0, countApplications());
        final var aName = "invoices-app";
        final var aProjectId = "1232454";

        final var aActualResponse = this.applicationRepository().existsByProjectIdAndName(aProjectId, aName);

        Assertions.assertFalse(aActualResponse);
    }

    @Test
    void givenAnExistsProjectIdAndName_whenCallExistsByProjectIdAndName_thenReturnTrue() {
        Assertions.assertEquals(0, countApplications());

        final var aName = "invoices-app";
        final var aDescription = "invoices app blablalbla";
        final var aProject = Fixture.ProjectFixture.newProject();
        final var aProjectId = aProject.getId();
        final var aRepositoryUrl = "http://localhost/java-template";
        final var aTeamId = aProject.getTeamId();
        final var aLanguage = LanguageType.JAVA;
        final var aApplicationType = ApplicationType.JOB;

        final var aApplication = Application.newApplication(
                aName,
                aDescription,
                aProjectId,
                aRepositoryUrl,
                aTeamId,
                aLanguage,
                aApplicationType
        );

        this.applicationRepository().save(aApplication);

        Assertions.assertEquals(1, countApplications());

        final var aActualResponse = this.applicationRepository().existsByProjectIdAndName(
                aProjectId.value().toString(), aName);

        Assertions.assertTrue(aActualResponse);
    }
}
