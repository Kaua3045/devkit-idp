package com.kaua.devkit.platform.infrastructure.gateways;

import com.kaua.devkit.platform.application.gateways.TemplateGateway;
import com.kaua.devkit.platform.domain.exceptions.DomainException;
import com.kaua.devkit.platform.infrastructure.configurations.properties.WebClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedHashMap;
import java.util.Objects;

@Component
public class TemplateGatewayImpl implements TemplateGateway {

    private static final Logger log = LoggerFactory.getLogger(TemplateGatewayImpl.class);

    private final String url;
    private final WebClient webClient;

    public TemplateGatewayImpl(final WebClient webClient, final WebClientProperties properties) {
        this.webClient = Objects.requireNonNull(webClient);
        this.url = properties.getBaseUrl();
    }

    @Override
    public CreateTemplateResponse generate(final CreateTemplateRequest request) {
        log.info("Generating template for [language:{}] [appType:{}]", request.language(), request.applicationType());

        final var aMap = new LinkedHashMap<>();
        aMap.put("projectName", request.applicationName());
        aMap.put("language", request.language());
        aMap.put("teamName", request.teamName());

        final var aOutput = this.webClient.post()
                .uri(url + "/v1/templates/generate")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(aMap)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (ex) -> {
                    System.out.println(ex);
                    throw DomainException.with("Error on create template");
                })
                .bodyToMono(CreateTemplateResponse.class)
                .block();

        log.info("Template created [repositoryUrl:{}]", aOutput.repositoryUrl());

        return aOutput;
    }

    @Override
    public void createTeam(final CreateTeamRequest request) {
        log.info("Creating team [teamName:{}]", request.teamName());

        final var aMap = new LinkedHashMap<>();
        aMap.put("teamName", request.teamName());
        aMap.put("maintainers", request.maintainers());

        this.webClient.post()
                .uri(url + "/v1/teams/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(aMap)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (ex) -> {
                    System.out.println(ex);
                    throw DomainException.with("Error on create team");
                })
                .bodyToMono(Void.class)
                .block();

        log.info("Team created [teamName:{}]", request.teamName());
    }
}
