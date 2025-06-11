package com.kaua.devkit.platform;

import com.kaua.devkit.platform.application.repositories.*;
import com.kaua.devkit.platform.infrastructure.applications.ApplicationJdbcRepository;
import com.kaua.devkit.platform.infrastructure.jdbc.JdbcClientAdapter;
import com.kaua.devkit.platform.infrastructure.oauth.code.AuthorizationCodeJdbcRepository;
import com.kaua.devkit.platform.infrastructure.oauth.token.AuthorizationTokenJdbcRepository;
import com.kaua.devkit.platform.infrastructure.projects.ProjectJdbcRepository;
import com.kaua.devkit.platform.infrastructure.teams.TeamJdbcRepository;
import com.kaua.devkit.platform.infrastructure.teams.TeamMemberJdbcRepository;
import com.kaua.devkit.platform.infrastructure.users.UserJdbcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;

@DataJdbcTest
@Tag("integrationTest")
@ActiveProfiles("test-integration")
public abstract class AbstractRepositoryTest {

    private static final String USERS_TABLE = "users";
    private static final String AUTHORIZATION_CODE_TABLE = "authorization_codes";
    private static final String AUTHORIZATION_TOKEN_TABLE = "authorization_tokens";
    private static final String TEAMS_TABLE = "teams";
    private static final String PROJECTS_TABLE = "projects";
    private static final String APPLICATIONS_TABLE = "applications";
    private static final String TEAMS_MEMBERS_TABLE = "teams_members";

    @Autowired
    private JdbcClient jdbcClient;

    private UserJdbcRepository userJdbcRepository;
    private AuthorizationCodeRepository authorizationCodeRepository;
    private AuthorizationTokenRepository authorizationTokenRepository;
    private TeamRepository teamRepository;
    private ProjectRepository projectRepository;
    private ApplicationRepository applicationRepository;
    private TeamMemberRepository teamMemberRepository;

    @BeforeEach
    void setUp() {
        this.userJdbcRepository = new UserJdbcRepository(new JdbcClientAdapter(jdbcClient));
        this.authorizationCodeRepository = new AuthorizationCodeJdbcRepository(new JdbcClientAdapter(jdbcClient));
        this.authorizationTokenRepository = new AuthorizationTokenJdbcRepository(new JdbcClientAdapter(jdbcClient));
        this.teamRepository = new TeamJdbcRepository(new JdbcClientAdapter(jdbcClient));
        this.projectRepository = new ProjectJdbcRepository(new JdbcClientAdapter(jdbcClient));
        this.applicationRepository = new ApplicationJdbcRepository(new JdbcClientAdapter(jdbcClient));
        this.teamMemberRepository = new TeamMemberJdbcRepository(new JdbcClientAdapter(jdbcClient));
    }

    protected int countUsers() {
        return JdbcTestUtils.countRowsInTable(jdbcClient, USERS_TABLE);
    }

    protected int countAuthorizationCodes() {
        return JdbcTestUtils.countRowsInTable(jdbcClient, AUTHORIZATION_CODE_TABLE);
    }

    protected int countAuthorizationTokens() {
        return JdbcTestUtils.countRowsInTable(jdbcClient, AUTHORIZATION_TOKEN_TABLE);
    }

    protected int countTeams() {
        return JdbcTestUtils.countRowsInTable(jdbcClient, TEAMS_TABLE);
    }

    protected int countProjects() {
        return JdbcTestUtils.countRowsInTable(jdbcClient, PROJECTS_TABLE);
    }

    protected int countApplications() {
        return JdbcTestUtils.countRowsInTable(jdbcClient, APPLICATIONS_TABLE);
    }

    protected int countTeamMembers() {
        return JdbcTestUtils.countRowsInTable(jdbcClient, TEAMS_MEMBERS_TABLE);
    }

    public UserJdbcRepository userRepository() {
        return userJdbcRepository;
    }

    public AuthorizationCodeRepository authorizationCodeRepository() {
        return authorizationCodeRepository;
    }

    public AuthorizationTokenRepository authorizationTokenRepository() {
        return authorizationTokenRepository;
    }

    public TeamRepository teamRepository() {
        return teamRepository;
    }

    public ProjectRepository projectRepository() {
        return projectRepository;
    }

    public ApplicationRepository applicationRepository() {
        return applicationRepository;
    }

    public TeamMemberRepository teamMemberRepository() {
        return teamMemberRepository;
    }
}
