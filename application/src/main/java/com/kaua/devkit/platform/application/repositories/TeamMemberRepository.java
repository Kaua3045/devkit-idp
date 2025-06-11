package com.kaua.devkit.platform.application.repositories;

import com.kaua.devkit.platform.domain.teams.TeamMember;

public interface TeamMemberRepository {

    TeamMember save(TeamMember teamMember);
}
