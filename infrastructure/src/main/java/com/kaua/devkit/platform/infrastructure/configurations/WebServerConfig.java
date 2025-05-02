package com.kaua.devkit.platform.infrastructure.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.kaua.devkit.platform")
public class WebServerConfig {
}
