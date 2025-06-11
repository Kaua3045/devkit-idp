package com.kaua.devkit.platform.infrastructure.applications;

import com.kaua.devkit.platform.application.repositories.ApplicationRepository;
import com.kaua.devkit.platform.domain.applications.Application;
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
public class ApplicationJdbcRepository implements ApplicationRepository {

    private static final Logger log = LoggerFactory.getLogger(ApplicationJdbcRepository.class);

    private final DatabaseClient databaseClient;

    public ApplicationJdbcRepository(final DatabaseClient databaseClient) {
        this.databaseClient = Objects.requireNonNull(databaseClient);
    }

    @Override
    public boolean existsByProjectIdAndName(final String projectId, final String name) {
        final var aSql = "SELECT COUNT(*) FROM applications WHERE project_id = :projectId AND name = :name";
        return this.databaseClient.count(aSql, Map.of("projectId", projectId, "name", name)) > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Application save(final Application aApplication) {
        if (aApplication.getVersion() == 0) {
            log.debug("Creating a new application: {}", aApplication);
            create(aApplication);
            log.info("Created application {}", aApplication);
        }

        aApplication.incrementVersion();
        return aApplication;
    }

    private void create(final Application aApplication) {
        final var aSql = """
                INSERT INTO applications (id, version, name, description, project_id, repository_url, team_id, language_type, application_type, is_deleted, created_at, updated_at, deleted_at)
                VALUES (:id, (:version + 1), :name, :description, :projectId, :repositoryUrl, :teamId, :language, :applicationType, :isDeleted, :createdAt, :updatedAt, :deletedAt)
                """;
        executeUpdate(aSql, aApplication);
    }

    private int executeUpdate(final String aSql, final Application aApplication) {
        final var aParams = new HashMap<String, Object>();
        aParams.put("id", aApplication.getId().value().toString()); // TODO verify this
        aParams.put("version", aApplication.getVersion());
        aParams.put("name", aApplication.getName());
        aParams.put("description", aApplication.getDescription());
        aParams.put("projectId", aApplication.getProjectId().value().toString());
        aParams.put("repositoryUrl", aApplication.getRepositoryUrl());
        aParams.put("teamId", aApplication.getTeamId().value().toString());
        aParams.put("language", aApplication.getLanguage().name());
        aParams.put("applicationType", aApplication.getApplicationType().name());
        aParams.put("isDeleted", aApplication.isDeleted());
        aParams.put("createdAt", aApplication.getCreatedAt());
        aParams.put("updatedAt", aApplication.getUpdatedAt());
        aParams.put("deletedAt", aApplication.getDeletedAt().orElse(null));
        return this.databaseClient.update(aSql, aParams);
    }
}
