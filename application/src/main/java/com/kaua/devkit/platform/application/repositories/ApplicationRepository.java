package com.kaua.devkit.platform.application.repositories;

import com.kaua.devkit.platform.domain.applications.Application;

public interface ApplicationRepository {

    boolean existsByProjectIdAndName(String projectId, String name);

    Application save(Application application);
}
