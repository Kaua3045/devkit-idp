package com.kaua.devkit.platform.infrastructure.configurations;

import com.kaua.devkit.platform.infrastructure.configurations.properties.WebClientProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
public class WebClientConfig {

    @Bean
    @ConfigurationProperties(prefix = "web-client.template-service")
    public WebClientProperties templateServiceProperties() {
        return new WebClientProperties();
    }

    @Bean
    public WebClient templateServiceClient(final WebClientProperties templateProperties) {
        return buildWebClient(templateProperties);
    }

    private WebClient buildWebClient(final WebClientProperties properties) {
        final var aHttpClient =  HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getConnectTimeout())
                .responseTimeout(Duration.ofMillis(properties.getReadTimeout()))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(properties.getReadTimeout()))
                                .addHandlerLast(new WriteTimeoutHandler(properties.getReadTimeout())));

        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(aHttpClient))
                .baseUrl(properties.getBaseUrl())
                .build();
    }
}
