package com.kaua.devkit.platform.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaua.devkit.platform.ApiTest;
import com.kaua.devkit.platform.ControllerTest;
import com.kaua.devkit.platform.application.usecases.projects.create.CreateProjectInput;
import com.kaua.devkit.platform.application.usecases.projects.create.CreateProjectOutput;
import com.kaua.devkit.platform.application.usecases.projects.create.CreateProjectUseCase;
import com.kaua.devkit.platform.domain.teams.TeamId;
import com.kaua.devkit.platform.domain.utils.IdentifierUtils;
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

@ControllerTest(controllers = ProjectAPI.class)
class ProjectAPITest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CreateProjectUseCase createProjectUseCase;

    @Captor
    private ArgumentCaptor<CreateProjectInput> createProjectInputCaptor;

    @Test
    void givenAValidRequest_whenCallCreateProject_thenReturnProjectIdAndProjectName() throws Exception {
        final var aProjectName = "invoices-project";
        final var aDescription = "invoice project blablababla";
        final var aTeamId = new TeamId(IdentifierUtils.generateNewMonotonicULID());

        final var aExpectedProjectID = ULID.random().toString();

        Mockito.when(createProjectUseCase.execute(any()))
                .thenAnswer(call -> new CreateProjectOutput(aProjectName, aExpectedProjectID));

        var json = """
                {
                    "project_name": "%s",
                    "description": "%s",
                    "team_id": "%s"
                }
                """.formatted(aProjectName, aDescription, aTeamId.value().toString());

        final var aRequest = MockMvcRequestBuilders.post("/v1/projects")
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
                .andExpect(jsonPath("$.project_id").value(aExpectedProjectID))
                .andExpect(jsonPath("$.project_name").value(aProjectName));

        Mockito.verify(createProjectUseCase, Mockito.times(1)).execute(createProjectInputCaptor.capture());

        final var aCreateInput = createProjectInputCaptor.getValue();

        Assertions.assertEquals(aProjectName, aCreateInput.projectName());
        Assertions.assertEquals(aDescription, aCreateInput.description());
        Assertions.assertEquals(aTeamId.value().toString(), aCreateInput.teamId());
    }
}
