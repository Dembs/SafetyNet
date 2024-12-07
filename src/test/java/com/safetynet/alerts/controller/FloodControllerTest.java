package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ResidentInfoDTO;
import com.safetynet.alerts.service.FloodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FloodController.class)
class FloodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FloodService floodService;

    @Test
    void getHouseholdsByStationsTest() throws Exception {

        List<String> stations = List.of("1", "2");
        Map<String, List<ResidentInfoDTO>> mockResponse = Map.of(
                "1509 Culver St", List.of(
                        new ResidentInfoDTO("John", "Doe", "841-874-6512", 40, List.of("med1_1","med1_2"), List.of("allergy1")),
                        new ResidentInfoDTO("Jane", "Doe", "987-654-3210", 10, List.of("med2"), List.of("allergy2"))
                ),
                "29 15th St", List.of(
                        new ResidentInfoDTO("Jacob", "Boyd", "111-222-3333", 35, List.of("med3"), List.of("allergy2"))
                )
        );

        when(floodService.getHouseholdsByStations(stations)).thenReturn(mockResponse);

        mockMvc.perform(get("/flood/stations").param("stations", "1", "2"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.['1509 Culver St']").isArray())
               .andExpect(jsonPath("$.['1509 Culver St'][0].firstName").value("John"))
               .andExpect(jsonPath("$.['1509 Culver St'][1].firstName").value("Jane"))
               .andExpect(jsonPath("$.['29 15th St'][0].firstName").value("Jacob"));
    }

}
