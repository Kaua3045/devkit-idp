package com.kaua.devkit.platform.infrastructure.configurations.usecases;

import com.kaua.devkit.platform.application.repositories.UserRepository;
import com.kaua.devkit.platform.application.usecases.users.create.CreateUserUseCase;
import com.kaua.devkit.platform.application.usecases.users.create.DefaultCreateUserUseCase;
import com.kaua.devkit.platform.application.usecases.users.retrive.get.DefaultGetUserByIdUseCase;
import com.kaua.devkit.platform.application.usecases.users.retrive.get.GetUserByIdUseCase;
import com.kaua.devkit.platform.domain.users.PasswordEncryption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class UserUseCaseConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(
            final PasswordEncryption passwordEncryption,
            final UserRepository userRepository
    ) {
        return new DefaultCreateUserUseCase(userRepository, passwordEncryption);
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(
            final UserRepository userRepository
    ) {
        return new DefaultGetUserByIdUseCase(userRepository);
    }
}
