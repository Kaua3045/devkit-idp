package com.kaua.devkit.platform.domain.applications;

import com.kaua.devkit.platform.domain.AggregateRoot;
import com.kaua.devkit.platform.domain.projects.ProjectId;
import com.kaua.devkit.platform.domain.teams.TeamId;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
import com.kaua.devkit.platform.domain.utils.InstantUtils;
import com.kaua.devkit.platform.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Optional;

public class Application extends AggregateRoot<ApplicationId> {

    private String name;
    private String description;
    private ProjectId projectId;
    private String repositoryUrl;
    private TeamId teamId;
    private LanguageType language;
    private ApplicationType applicationType;
    private boolean isDeleted;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Application(
            final ApplicationId aApplicationId,
            final long aVersion,
            final String aName,
            final String aDescription,
            final ProjectId aProjectId,
            final String aRepositoryUrl,
            final TeamId aTeamId,
            final LanguageType aLanguage,
            final ApplicationType aApplicationType,
            final boolean aIsDeleted,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt
    ) {
        super(aApplicationId, aVersion);
        setName(aName);
        setDescription(aDescription);
        setProjectId(aProjectId);
        setRepositoryUrl(aRepositoryUrl);
        setTeamId(aTeamId);
        setLanguage(aLanguage);
        setApplicationType(aApplicationType);
        setDeleted(aIsDeleted);
        setCreatedAt(aCreatedAt);
        setUpdatedAt(aUpdatedAt);
        setDeletedAt(aDeletedAt);
    }

    public static Application newApplication(
            final String aName,
            final String aDescription,
            final ProjectId aProjectId,
            final String aRepositoryUrl,
            final TeamId aTeamId,
            final LanguageType aLanguage,
            final ApplicationType aApplicationType
    ) {
        final var aId = new ApplicationId(IdentifierUtils.generateNewMonotonicULID());
        final var aNow = InstantUtils.now();

        return new Application(
                aId,
                0L,
                aName,
                aDescription,
                aProjectId,
                aRepositoryUrl,
                aTeamId,
                aLanguage,
                aApplicationType,
                false,
                aNow,
                aNow,
                null
        );
    }

    public static Application with(
            final ApplicationId aApplicationId,
            final long aVersion,
            final String aName,
            final String aDescription,
            final ProjectId aProjectId,
            final String aRepositoryUrl,
            final TeamId aTeamId,
            final LanguageType aLanguage,
            final ApplicationType aApplicationType,
            final boolean aIsDeleted,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt
    ) {
        return new Application(
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
                aCreatedAt,
                aUpdatedAt,
                aDeletedAt
        );
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public TeamId getTeamId() {
        return teamId;
    }

    public LanguageType getLanguage() {
        return language;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Optional<Instant> getDeletedAt() {
        return Optional.ofNullable(deletedAt);
    }

    private void setName(final String name) {
        // TODO check lengths
        this.assertArgumentNotEmpty(name, "name", "should not be empty");
        this.assertArgumentMinLength(name, 5, "name", "should have at least 5 characters");
        this.assertArgumentMaxLength(name, 100, "name", "should have at most 100 characters");
        this.name = name;
    }

    private void setDescription(final String description) {
        this.assertArgumentNotEmpty(description, "description", "should not be empty");
        this.assertArgumentMinLength(description, 5, "description", "should have at least 5 characters");
        this.assertArgumentMaxLength(description, 100, "description", "should have at most 100 characters");
        this.description = description;
    }

    private void setProjectId(final ProjectId projectId) {
        this.projectId = this.assertArgumentNotNull(projectId, "projectId", "should not be null");
    }

    private void setRepositoryUrl(final String repositoryUrl) {
        this.repositoryUrl = this.assertArgumentNotEmpty(repositoryUrl, "repositoryUrl", "should not be empty");
    }

    private void setTeamId(TeamId teamId) {
        this.teamId = this.assertArgumentNotNull(teamId, "teamId", "should not be null");
    }

    private void setLanguage(final LanguageType language) {
        this.language = this.assertArgumentNotNull(language, "language", "should not be null");
    }

    private void setApplicationType(final ApplicationType applicationType) {
        this.applicationType = this.assertArgumentNotNull(applicationType, "applicationType", "should not be null");
    }

    private void setDeleted(final boolean deleted) {
        isDeleted = deleted;
    }

    private void setCreatedAt(final Instant createdAt) {
        this.createdAt = this.assertArgumentNotNull(createdAt, "createdAt", "should not be null");
    }

    private void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = this.assertArgumentNotNull(updatedAt, "updatedAt", "should not be null");
    }

    private void setDeletedAt(final Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public void validate(ValidationHandler aHandler) {

    }

    @Override
    public String toString() {
        return "Application(" +
                "id='" + getId().value().toString() + '\'' +
                ", version='" + getVersion() + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectId=" + projectId.value().toString() +
                ", repositoryUrl='" + repositoryUrl + '\'' +
                ", teamId=" + teamId.value().toString() +
                ", language=" + language.name() +
                ", applicationType=" + applicationType.name() +
                ", isDeleted=" + isDeleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + getDeletedAt().orElse(null) +
                ')';
    }
}
