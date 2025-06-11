CREATE TABLE applications (
    id VARCHAR(26) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL,
    project_id VARCHAR(26) NOT NULL,
    repository_url VARCHAR(500) NOT NULL,
    team_id VARCHAR(26) NOT NULL,
    language_type VARCHAR(20) NOT NULL,
    application_type VARCHAR(50) NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE,
    version BIGINT NOT NULL
);

CREATE UNIQUE INDEX unq_applications_projectId_name ON applications(project_id, name);