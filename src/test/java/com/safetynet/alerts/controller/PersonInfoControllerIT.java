package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonInfoDTO;
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
public class PersonInfoControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getPersonsByLastNameIT() throws Exception {
        String lastName = "Boyd";

        mockMvc.perform(get("/personInfo")
                       .param("lastName", lastName)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", not(empty())))
               .andExpect(jsonPath("$.length()", greaterThan(0)))
               .andExpect(jsonPath("$[0].firstName", notNullValue()))
               .andExpect(jsonPath("$[0].lastName", equalTo(lastName)))
               .andExpect(jsonPath("$[0].age", greaterThanOrEqualTo(0)))
               .andExpect(jsonPath("$[0].medications", not(empty())))
               .andExpect(jsonPath("$[0].allergies", notNullValue()));
    }

}
