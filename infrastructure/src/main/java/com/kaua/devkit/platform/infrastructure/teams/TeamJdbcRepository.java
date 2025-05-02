package com.kaua.devkit.platform.infrastructure.teams;

import com.kaua.devkit.platform.application.repositories.TeamRepository;
import com.kaua.devkit.platform.domain.teams.Team;
import com.kaua.devkit.platform.infrastructure.jdbc.DatabaseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class TeamJdbcRepository implements TeamRepository {

    private static final Logger log = LoggerFactory.getLogger(TeamJdbcRepository.class);

    private final DatabaseClient databaseClient;

    public TeamJdbcRepository(final DatabaseClient databaseClient) {
        this.databaseClient = Objects.requireNonNull(databaseClient);
    }

    @Override
    public boolean existsByTeamName(final String teamName) {
        final var aSql = "SELECT COUNT(*) FROM teams WHERE team_name = :teamName";
        return this.databaseClient.count(aSql, Map.of("teamName", teamName)) > 0;
    }

    @Override
    public boolean existsByTeamId(final String id) {
        final var aSql = "SELECT COUNT(*) FROM teams WHERE id = :id";
        return this.databaseClient.count(aSql, Map.of("id", id)) > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Team save(final Team aTeam) {
        if (aTeam.getVersion() == 0) {
            log.debug("Creating a new team: {}", aTeam);
            create(aTeam);
            log.info("Created team {}", aTeam);
        }

        aTeam.incrementVersion();
        return aTeam;
    }

    private void create(final Team aTeam) {
        final var aSql = """
                INSERT INTO teams (id, version, team_name, created_at, updated_at)
                VALUES (:id, (:version + 1), :team_name, :created_at, :updated_at)
                """;
        executeUpdate(aSql, aTeam);
    }

    private int executeUpdate(final String aSql, final Team aTeam) {
        final var aParams = new HashMap<String, Object>();
        aParams.put("id", aTeam.getId().value().toString()); // TODO verify this
        aParams.put("version", aTeam.getVersion());
        aParams.put("team_name", aTeam.getTeamName());
        aParams.put("created_at", aTeam.getCreatedAt());
        aParams.put("updated_at", aTeam.getUpdatedAt());
        return this.databaseClient.update(aSql, aParams);
    }
}
