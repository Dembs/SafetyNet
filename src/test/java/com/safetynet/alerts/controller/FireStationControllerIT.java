package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FireStationControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getAllFireStationsIT() throws Exception {
        mockMvc.perform(get("/firestation")
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", not(empty())))
               .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @Test
    void addOneFireStationIT() throws Exception {
        String newFireStation = """
                {
                    "address": "123 Test St",
                    "station": "5"
                }
                """;

        mockMvc.perform(post("/firestation")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(newFireStation))
               .andExpect(status().isOk())
               .andExpect(content().string("Fire Station added successfully!"));
    }

    @Test
    void deleteOneFireStationIT() throws Exception {
        String fireStationToDelete = """
                {
                    "address": "123 Test St",
                    "station": "5"
                }
                """;

        mockMvc.perform(delete("/firestation")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(fireStationToDelete))
               .andExpect(status().isOk());
    }

    @Test
    void updateOneFireStationIT() throws Exception {
        String updatedFireStation = """
                {
                    "address": "1509 Culver St",
                    "station": "2"
                }
                """;

        mockMvc.perform(put("/firestation")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(updatedFireStation))
               .andExpect(status().isOk());
    }

    @Test
    void getPersonsByStationIT() throws Exception {
        String stationNumber = "1";

        mockMvc.perform(get("/firestation")
                       .param("stationNumber", stationNumber)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.adults", greaterThanOrEqualTo(0)))
               .andExpect(jsonPath("$.children", greaterThanOrEqualTo(0)))
               .andExpect(jsonPath("$.persons", not(empty())));
    }
}
