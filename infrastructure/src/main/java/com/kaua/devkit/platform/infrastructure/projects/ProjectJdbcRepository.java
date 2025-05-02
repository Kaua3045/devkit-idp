package com.kaua.devkit.platform.infrastructure.projects;

import com.kaua.devkit.platform.application.repositories.ProjectRepository;
import com.kaua.devkit.platform.domain.projects.Project;
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
public class ProjectJdbcRepository implements ProjectRepository {

    private static final Logger log = LoggerFactory.getLogger(ProjectJdbcRepository.class);

    private final DatabaseClient databaseClient;

    public ProjectJdbcRepository(final DatabaseClient databaseClient) {
        this.databaseClient = Objects.requireNonNull(databaseClient);
    }

    @Override
    public boolean existsByProjectName(final String projectName) {
        final var aSql = "SELECT COUNT(*) FROM projects WHERE project_name = :projectName";
        return this.databaseClient.count(aSql, Map.of("projectName", projectName)) > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Project save(final Project aProject) {
        if (aProject.getVersion() == 0) {
            log.debug("Creating a new project: {}", aProject);
            create(aProject);
            log.info("Created project {}", aProject);
        }

        aProject.incrementVersion();
        return aProject;
    }

    private void create(final Project aProject) {
        final var aSql = """
                INSERT INTO projects (id, version, project_name, description, team_id, created_at, updated_at)
                VALUES (:id, (:version + 1), :project_name, :description, :team_id, :created_at, :updated_at)
                """;
        executeUpdate(aSql, aProject);
    }

    private int executeUpdate(final String aSql, final Project aProject) {
        final var aParams = new HashMap<String, Object>();
        aParams.put("id", aProject.getId().value().toString()); // TODO verify this
        aParams.put("version", aProject.getVersion());
        aParams.put("project_name", aProject.getProjectName());
        aParams.put("description", aProject.getDescription());
        aParams.put("team_id", aProject.getTeamId().value().toString());
        aParams.put("created_at", aProject.getCreatedAt());
        aParams.put("updated_at", aProject.getUpdatedAt());
        return this.databaseClient.update(aSql, aParams);
    }
}
