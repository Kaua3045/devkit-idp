package com.kaua.devkit.platform.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaua.devkit.platform.ApiTest;
import com.kaua.devkit.platform.ControllerTest;
import com.kaua.devkit.platform.application.usecases.teams.create.CreateTeamInput;
import com.kaua.devkit.platform.application.usecases.teams.create.CreateTeamOutput;
import com.kaua.devkit.platform.application.usecases.teams.create.CreateTeamUseCase;
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

@ControllerTest(controllers = TeamAPI.class)
class TeamAPITest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CreateTeamUseCase createTeamUseCase;

    @Captor
    private ArgumentCaptor<CreateTeamInput> createTeamInputCaptor;

    @Test
    void givenAValidRequest_whenCallCreateTeam_thenReturnTeamIdAndTeamName() throws Exception {
        final var aTeamName = "invoice-team";
        final var aOwnerId = "1234567890";

        final var aExpectedTeamID = ULID.random().toString();

        Mockito.when(createTeamUseCase.execute(any()))
                .thenAnswer(call -> new CreateTeamOutput(aTeamName, aExpectedTeamID));

        var json = """
                {
                    "team_name": "%s",
                    "owner_id": "%s"
                }
                """.formatted(aTeamName, aOwnerId);

        final var aRequest = MockMvcRequestBuilders.post("/v1/teams")
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
                .andExpect(jsonPath("$.team_id").value(aExpectedTeamID))
                .andExpect(jsonPath("$.team_name").value(aTeamName));

        Mockito.verify(createTeamUseCase, Mockito.times(1)).execute(createTeamInputCaptor.capture());

        final var aCreateInput = createTeamInputCaptor.getValue();

        Assertions.assertEquals(aTeamName, aCreateInput.teamName());
        Assertions.assertEquals(aOwnerId, aCreateInput.ownerId());
    }
}
