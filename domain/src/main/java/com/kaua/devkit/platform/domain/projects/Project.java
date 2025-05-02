package com.kaua.devkit.platform.domain.projects;

import com.kaua.devkit.platform.domain.AggregateRoot;
import com.kaua.devkit.platform.domain.teams.TeamId;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
import com.kaua.devkit.platform.domain.utils.InstantUtils;
import com.kaua.devkit.platform.domain.validation.ValidationHandler;

import java.time.Instant;

public class Project extends AggregateRoot<ProjectId> {

    private String projectName;
    private String description;
    private TeamId teamId;
    private Instant createdAt;
    private Instant updatedAt;

    private Project(
            final ProjectId aProjectId,
            final long aVersion,
            final String aProjectName,
            final String aDescription,
            final TeamId aTeamId,
            final Instant aCreatedAt,
            final Instant aUpdatedAt
    ) {
        super(aProjectId, aVersion);
        setProjectName(aProjectName);
        setDescription(aDescription);
        setTeamId(aTeamId);
        setCreatedAt(aCreatedAt);
        setUpdatedAt(aUpdatedAt);
    }

    public static Project newProject(
            final String aProjectName,
            final String aDescription,
            final TeamId aTeamId
    ) {
        final var aId = new ProjectId(IdentifierUtils.generateNewMonotonicULID());
        final var aNow = InstantUtils.now();

        return new Project(
                aId,
                0L,
                aProjectName,
                aDescription,
                aTeamId,
                aNow,
                aNow
        );
    }

    public static Project with(
            final ProjectId aProjectId,
            final long aVersion,
            final String aProjectName,
            final String aDescription,
            final TeamId aTeamId,
            final Instant aCreatedAt,
            final Instant aUpdatedAt
    ) {
        return new Project(
                aProjectId,
                aVersion,
                aProjectName,
                aDescription,
                aTeamId,
                aCreatedAt,
                aUpdatedAt
        );
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }

    public TeamId getTeamId() {
        return teamId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    private void setProjectName(final String projectName) {
        this.assertArgumentNotEmpty(projectName, "projectName", "should not be empty");
        this.assertArgumentMinLength(projectName, 5, "projectName", "should have at least 5 characters");
        this.assertArgumentMaxLength(projectName, 100, "projectName", "should have at most 100 characters");
        this.projectName = projectName;
    }

    private void setDescription(final String description) {
        this.assertArgumentNotEmpty(description, "description", "should not be empty");
        this.assertArgumentMinLength(description, 20, "description", "should have at least 20 characters");
        this.assertArgumentMaxLength(description, 100, "description", "should have at most 100 characters");
        this.description = description;
    }

    private void setTeamId(final TeamId teamId) {
        this.teamId = this.assertArgumentNotNull(teamId, "teamId", "should not be null");
    }

    private void setCreatedAt(Instant createdAt) {
        this.createdAt = this.assertArgumentNotNull(createdAt, "createdAt", "should not be null");
    }

    private void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = this.assertArgumentNotNull(updatedAt, "updatedAt", "should not be null");
    }

    @Override
    public void validate(ValidationHandler aHandler) {
    }

    @Override
    public String toString() {
        return "Project(" +
                "id='" + getId().value().toString() + '\'' +
                ", version='" + getVersion() + '\'' +
                ", projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", teamId=" + teamId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ')';
    }
}
