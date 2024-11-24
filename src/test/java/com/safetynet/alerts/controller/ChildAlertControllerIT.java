package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildInfoDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChildAlertControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getChildrenAtAddressIT() throws Exception {

        String testAddress = "1509 Culver St";

        mockMvc.perform(get("/childAlert")
                       .param("address", testAddress)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(greaterThan(0))))
               .andExpect(jsonPath("$[0].firstName", is("Tenley")))
               .andExpect(jsonPath("$[0].lastName", is("Boyd")))
               .andExpect(jsonPath("$[0].age", is(12)))
               .andExpect(jsonPath("$[0].familyMembers", hasSize(greaterThan(0))))
               .andExpect(jsonPath("$[0].familyMembers[0].firstName", is("John")))
               .andExpect(jsonPath("$[0].familyMembers[0].lastName", is("Boyd")));
    }

}
