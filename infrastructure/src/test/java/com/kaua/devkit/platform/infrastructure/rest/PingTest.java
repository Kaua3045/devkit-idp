package com.kaua.devkit.platform.infrastructure.rest;

import com.kaua.devkit.platform.ApiTest;
import com.kaua.devkit.platform.ControllerTest;
import com.kaua.devkit.platform.infrastructure.rest.controllers.Ping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(controllers = Ping.class)
public class PingTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testCallPingControllerThenReturnPong() throws Exception {
        final var aRequest = MockMvcRequestBuilders.get("/ping")
                .with(ApiTest.admin())
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        final var aResponse = this.mvc.perform(aRequest);

        aResponse
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
