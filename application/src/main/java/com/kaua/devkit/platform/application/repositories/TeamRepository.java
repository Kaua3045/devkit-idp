package com.kaua.devkit.platform.application.repositories;

import com.kaua.devkit.platform.domain.teams.Team;

public interface TeamRepository {

    boolean existsByTeamName(String teamName);

    boolean existsByTeamId(String id);

    Team save(Team team);
}
