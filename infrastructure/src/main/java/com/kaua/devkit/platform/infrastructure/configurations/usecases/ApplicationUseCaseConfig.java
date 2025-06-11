package com.kaua.devkit.platform.infrastructure.configurations.usecases;

import com.kaua.devkit.platform.application.gateways.TemplateGateway;
import com.kaua.devkit.platform.application.repositories.ApplicationRepository;
import com.kaua.devkit.platform.application.usecases.applications.create.CreateApplicationUseCase;
import com.kaua.devkit.platform.application.usecases.applications.create.DefaultCreateApplicationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ApplicationUseCaseConfig {

    @Bean
    public CreateApplicationUseCase createApplicationUseCase(
            final TemplateGateway templateGateway,
            final ApplicationRepository applicationRepository
    ) {
        return new DefaultCreateApplicationUseCase(
                applicationRepository,
                templateGateway
        );
    }
}
