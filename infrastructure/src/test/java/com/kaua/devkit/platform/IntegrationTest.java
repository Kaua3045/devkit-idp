package com.kaua.devkit.platform;

import com.kaua.devkit.platform.infrastructure.configurations.WebServerConfig;
import com.kaua.devkit.platform.infrastructure.configurations.properties.OAuthClients;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-integration")
@SpringBootTest(classes = {
        WebServerConfig.class,
        IntegrationTestConfig.class,
        OAuthClients.class
})
@Tag("integrationTest")
public @interface IntegrationTest {
}
