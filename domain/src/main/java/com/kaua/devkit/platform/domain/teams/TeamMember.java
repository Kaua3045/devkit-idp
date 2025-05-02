package com.kaua.devkit.platform.domain.teams;

import com.kaua.devkit.platform.domain.AggregateRoot;
import com.kaua.devkit.platform.domain.users.UserID;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
import com.kaua.devkit.platform.domain.utils.InstantUtils;
import com.kaua.devkit.platform.domain.validation.ValidationHandler;

import java.time.Instant;

public class TeamMember extends AggregateRoot<TeamMemberId> {

    private UserID userId;
    private TeamMemberRole role;
    private Instant createdAt;
    private Instant updatedAt;

    private TeamMember(
            final TeamMemberId aTeamMemberId,
            final long aVersion,
            final UserID aUserId,
            final TeamMemberRole aRole,
            final Instant aCreatedAt,
            final Instant aUpdatedAt
            
    ) {
        super(aTeamMemberId, aVersion);
        setUserId(aUserId);
        setRole(aRole);
        setCreatedAt(aCreatedAt);
        setUpdatedAt(aUpdatedAt);
    }

    public static TeamMember newMember(
            final UserID aUserId,
            final TeamMemberRole aRole
    ) {
        final var aId = new TeamMemberId(IdentifierUtils.generateNewMonotonicULID());
        final var aNow = InstantUtils.now();

        return new TeamMember(
                aId,
                0L,
                aUserId,
                aRole,
                aNow,
                aNow
        );
    }

    public static TeamMember with(
            final TeamMemberId aTeamMemberId,
            final long aVersion,
            final UserID aUserId,
            final TeamMemberRole aRole,
            final Instant aCreatedAt,
            final Instant aUpdatedAt
    ) {
        return new TeamMember(
                aTeamMemberId,
                aVersion,
                aUserId,
                aRole,
                aCreatedAt,
                aUpdatedAt
        );
    }

    public UserID getUserId() {
        return userId;
    }

    public TeamMemberRole getRole() {
        return role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    private void setUserId(final UserID userId) {
        this.userId = this.assertArgumentNotNull(userId, "userId", "should not be null");
    }

    private void setRole(final TeamMemberRole role) {
        this.role = this.assertArgumentNotNull(role, "role", "should not be null");
    }

    private void setCreatedAt(final Instant createdAt) {
        this.createdAt = this.assertArgumentNotNull(createdAt, "createdAt", "should not be null");;
    }

    private void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = this.assertArgumentNotNull(updatedAt, "updatedAt", "should not be null");
    }

    @Override
    public void validate(ValidationHandler aHandler) {

    }

    @Override
    public String toString() {
        return "TeamMember(" +
                "id=" + getId().value().toString() +
                ", version=" + getVersion() +
                ", userId=" + getUserId().value().toString() +
                ", role=" + role.name() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ')';
    }
}
