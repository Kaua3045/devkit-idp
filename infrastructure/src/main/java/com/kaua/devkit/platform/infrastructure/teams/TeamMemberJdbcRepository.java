package com.kaua.devkit.platform.infrastructure.teams;

import com.kaua.devkit.platform.application.repositories.TeamMemberRepository;
import com.kaua.devkit.platform.domain.teams.TeamMember;
import com.kaua.devkit.platform.infrastructure.jdbc.DatabaseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Objects;

@Component
public class TeamMemberJdbcRepository implements TeamMemberRepository {

    private static final Logger log = LoggerFactory.getLogger(TeamMemberJdbcRepository.class);

    private final DatabaseClient databaseClient;

    public TeamMemberJdbcRepository(final DatabaseClient databaseClient) {
        this.databaseClient = Objects.requireNonNull(databaseClient);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public TeamMember save(final TeamMember aTeamMember) {
        if (aTeamMember.getVersion() == 0) {
            log.debug("Creating a new team member: {}", aTeamMember);
            create(aTeamMember);
            log.info("Created team member {}", aTeamMember);
        }

        aTeamMember.incrementVersion();
        return aTeamMember;
    }

    private void create(final TeamMember aTeamMember) {
        final var aSql = """
                INSERT INTO teams_members (id, version, user_id, role, created_at, updated_at)
                VALUES (:id, (:version + 1), :userId, :role, :created_at, :updated_at)
                """;
        executeUpdate(aSql, aTeamMember);
    }

    private int executeUpdate(final String aSql, final TeamMember aTeamMember) {
        final var aParams = new HashMap<String, Object>();
        aParams.put("id", aTeamMember.getId().value().toString()); // TODO verify this
        aParams.put("version", aTeamMember.getVersion());
        aParams.put("userId", aTeamMember.getUserId().value().toString());
        aParams.put("role", aTeamMember.getRole().name());
        aParams.put("created_at", aTeamMember.getCreatedAt());
        aParams.put("updated_at", aTeamMember.getUpdatedAt());
        return this.databaseClient.update(aSql, aParams);
    }
}
