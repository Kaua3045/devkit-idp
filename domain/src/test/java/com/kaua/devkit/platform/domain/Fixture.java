package com.kaua.devkit.platform.domain;

import com.kaua.devkit.platform.domain.projects.Project;
import com.kaua.devkit.platform.domain.teams.Team;
import com.kaua.devkit.platform.domain.teams.TeamId;
import com.kaua.devkit.platform.domain.users.*;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
import net.datafaker.Faker;

import java.util.ArrayList;

public final class Fixture {

    private static final Faker faker = new Faker();

    private Fixture() {}


    public static final class UserFixture {
        private UserFixture() {}

        public static User newUser() {
            return User.newUser(
                    new Name(faker.name().firstName(), faker.name().lastName()),
                    new Email(faker.internet().emailAddress()),
                    Password.of("12345678Am*"),
                    UserRole.USER
            );
        }
    }

    public static final class TeamFixture {
        private TeamFixture() {}

        public static Team newTeam() {
            return Team.newTeam(
                    "invoices-team",
                    new ArrayList<>()
            );
        }
    }

    public static final class ProjectFixture {
        private ProjectFixture() {}

        public static Project newProject() {
            return Project.newProject(
                    "invoices-project",
                    "invoices-project bla bla bla",
                    new TeamId(IdentifierUtils.generateNewMonotonicULID())
            );
        }
    }
}
