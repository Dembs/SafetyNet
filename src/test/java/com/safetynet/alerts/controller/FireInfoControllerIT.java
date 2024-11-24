package com.safetynet.alerts.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FireInfoControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getResidentsByValidAddressIT() throws Exception {

        String testAddress = "1509 Culver St";

        mockMvc.perform(get("/fire")
                       .param("address", testAddress)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.fireStationNumber", is("3")))
               .andExpect(jsonPath("$.residents", hasSize(greaterThan(0))))
               .andExpect(jsonPath("$.residents[0].firstName", notNullValue()))
               .andExpect(jsonPath("$.residents[0].lastName", notNullValue()))
               .andExpect(jsonPath("$.residents[0].phone", notNullValue()))
               .andExpect(jsonPath("$.residents[0].age", greaterThan(0)))
               .andExpect(jsonPath("$.residents[0].medications", notNullValue()))
               .andExpect(jsonPath("$.residents[0].allergies", notNullValue()));
    }

}
