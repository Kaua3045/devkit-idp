package com.kaua.devkit.platform.application.repositories;

import com.kaua.devkit.platform.domain.users.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> userOfEmail(String email);

    Optional<User> userOfId(String id);

    boolean existsByEmail(String email);

    User save(User user);
}
