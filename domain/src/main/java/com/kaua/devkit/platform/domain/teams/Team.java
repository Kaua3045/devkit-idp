package com.kaua.devkit.platform.domain.teams;

import com.kaua.devkit.platform.domain.AggregateRoot;
import com.kaua.devkit.platform.domain.Identifier;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
import com.kaua.devkit.platform.domain.utils.InstantUtils;
import com.kaua.devkit.platform.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team extends AggregateRoot<TeamId> {

    private String teamName;
    private List<TeamMemberId> teamMemberIds;
    private Instant createdAt;
    private Instant updatedAt;

    private Team(
            final TeamId aTeamId,
            final long aVersion,
            final String aTeamName,
            final List<TeamMemberId> aTeamMemberIds,
            final Instant aCreatedAt,
            final Instant aUpdatedAt
    ) {
        super(aTeamId, aVersion);
        setTeamName(aTeamName);
        setTeamMemberIds(aTeamMemberIds == null ? new ArrayList<>() : aTeamMemberIds);
        setCreatedAt(aCreatedAt);
        setUpdatedAt(aUpdatedAt);
    }

    public static Team newTeam(
            final String aTeamName,
            final List<TeamMemberId> aTeamMemberIds
    ) {
        final var aId = new TeamId(IdentifierUtils.generateNewMonotonicULID());
        final var aNow = InstantUtils.now();

        return new Team(
                aId,
                0L,
                aTeamName,
                aTeamMemberIds,
                aNow,
                aNow
        );
    }

    public static Team with(
            final TeamId aTeamId,
            final long aVersion,
            final String aTeamName,
            final List<TeamMemberId> aTeamMembersId,
            final Instant aCreatedAt,
            final Instant aUpdatedAt
    ) {
        return new Team(
                aTeamId,
                aVersion,
                aTeamName,
                aTeamMembersId,
                aCreatedAt,
                aUpdatedAt
        );
    }

    public String getTeamName() {
        return teamName;
    }

    public List<TeamMemberId> getTeamMemberIds() {
        return Collections.unmodifiableList(teamMemberIds);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    private void setTeamName(final String teamName) {
        this.assertArgumentNotEmpty(teamName, "teamName", "should not be empty");
        this.assertArgumentMinLength(teamName, 5, "teamName", "should have at least 5 characters");
        this.assertArgumentMaxLength(teamName, 100, "teamName", "should have at most 100 characters");
        this.teamName = teamName;
    }

    private void setTeamMemberIds(final List<TeamMemberId> teamMemberIds) {
        this.teamMemberIds = teamMemberIds;
    }

    private void setCreatedAt(final Instant createdAt) {
        this.createdAt = this.assertArgumentNotNull(createdAt, "createdAt", "should not be null");
    }

    private void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = this.assertArgumentNotNull(updatedAt, "updatedAt", "should not be null");
    }

    @Override
    public void validate(ValidationHandler aHandler) {

    }

    @Override
    public String toString() {
        return "Team(" +
                "id='" + getId().value().toString() + '\'' +
                ", version=" + getVersion() +
                ", teamName=" + teamName +
                ", teamMemberIds=" + teamMemberIds +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ')';
    }
}
