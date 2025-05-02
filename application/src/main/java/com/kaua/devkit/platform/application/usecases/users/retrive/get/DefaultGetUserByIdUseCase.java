package com.kaua.devkit.platform.application.usecases.users.retrive.get;

import com.kaua.devkit.platform.application.exceptions.UseCaseInputCannotBeNullException;
import com.kaua.devkit.platform.application.repositories.UserRepository;
import com.kaua.devkit.platform.domain.exceptions.NotFoundException;
import com.kaua.devkit.platform.domain.users.User;

import java.util.Objects;

public class DefaultGetUserByIdUseCase extends GetUserByIdUseCase {

    private final UserRepository userRepository;

    public DefaultGetUserByIdUseCase(final UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    @Override
    public GetUserByIdOutput execute(final GetUserByIdInput input) {
        if (input == null) throw new UseCaseInputCannotBeNullException(GetUserByIdUseCase.class);

        return this.userRepository.userOfId(input.id())
                .map(GetUserByIdOutput::from)
                .orElseThrow(NotFoundException.with(User.class, input.id()));
    }
}
