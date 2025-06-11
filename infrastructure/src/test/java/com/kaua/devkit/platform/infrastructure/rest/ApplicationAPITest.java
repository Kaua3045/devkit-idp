package com.kaua.devkit.platform.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaua.devkit.platform.ApiTest;
import com.kaua.devkit.platform.ControllerTest;
import com.kaua.devkit.platform.application.usecases.applications.create.CreateApplicationInput;
import com.kaua.devkit.platform.application.usecases.applications.create.CreateApplicationOutput;
import com.kaua.devkit.platform.application.usecases.applications.create.CreateApplicationUseCase;
import com.kaua.devkit.platform.domain.utils.ULID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = ApplicationAPI.class)
class ApplicationAPITest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CreateApplicationUseCase createApplicationUseCase;

    @Captor
    private ArgumentCaptor<CreateApplicationInput> createApplicationInputCaptor;

    @Test
    void givenAValidRequest_whenCallCreateApplication_thenReturnApplicationIdAndRepositoryUrl() throws Exception {
        final var aName = "invoices-app";
        final var aDescription = "invoice microservice to generate invoices";
        final var aProjectId = ULID.random().toString();
        final var aTeamId = ULID.random().toString();
        final var aTeamName = "invoices-team";
        final var aLanguage = "GO";
        final var aApplicationType = "WEB";

        final var aExpectedApplicationID = ULID.random().toString();
        final var aExpectedRepositoryUrl = "http://localhost/repo-java";

        Mockito.when(createApplicationUseCase.execute(any()))
                .thenAnswer(call -> new CreateApplicationOutput(aExpectedApplicationID, aExpectedRepositoryUrl));

        var json = """
                {
                    "name": "%s",
                    "description": "%s",
                    "project_id": "%s",
                    "team_id": "%s",
                    "team_name": "%s",
                    "language": "%s",
                    "application_type": "%s"
                }
                """.formatted(
                aName,
                aDescription,
                aProjectId,
                aTeamId,
                aTeamName,
                aLanguage,
                aApplicationType
        );

        final var aRequest = MockMvcRequestBuilders.post("/v1/applications")
                .with(ApiTest.admin())
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json);

        final var aResponse = this.mvc.perform(aRequest);

        aResponse
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.application_id").value(aExpectedApplicationID))
                .andExpect(jsonPath("$.repository_url").value(aExpectedRepositoryUrl));

        Mockito.verify(createApplicationUseCase, Mockito.times(1)).execute(createApplicationInputCaptor.capture());

        final var aCreateInput = createApplicationInputCaptor.getValue();

        Assertions.assertEquals(aName, aCreateInput.name());
        Assertions.assertEquals(aDescription, aCreateInput.description());
        Assertions.assertEquals(aProjectId, aCreateInput.projectId());
        Assertions.assertEquals(aTeamId, aCreateInput.teamId());
        Assertions.assertEquals(aTeamName, aCreateInput.teamName());
        Assertions.assertEquals(aLanguage, aCreateInput.language());
        Assertions.assertEquals(aApplicationType, aCreateInput.applicationType());
    }
}
