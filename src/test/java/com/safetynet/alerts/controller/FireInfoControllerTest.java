package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ResidentInfoDTO;
import com.safetynet.alerts.service.FireInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FireInfoController.class)
class FireInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireInfoService fireInfoService;

    @Test
    void getResidentsByAddress() throws Exception {
        String address = "1509 Culver St";
        Map<String, Object> mockResponse = Map.of(
                "fireStationNumber", "1",
                "residents", List.of(
                        new ResidentInfoDTO("John", "Boyd", "123-456-7890", 40, List.of("med1"), List.of("allergy1")),
                        new ResidentInfoDTO("Jane", "Doe", "987-654-3210", 10, List.of("med2"), List.of())
                )
        );

        when(fireInfoService.getResidentsByAddress(address)).thenReturn(mockResponse);

        mockMvc.perform(get("/fire").param("address", address))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.fireStationNumber").value("1"))
               .andExpect(jsonPath("$.residents.length()").value(2))
               .andExpect(jsonPath("$.residents[0].firstName").value("John"))
               .andExpect(jsonPath("$.residents[1].age").value(10));
    }
    @Test
    void getResidentsByAddress_EmptyResponseTest() throws Exception {
        String address = "123 test";
        Map<String, Object> emptyResponse = new HashMap<>();
        emptyResponse.put("fireStationNumber", null);
        emptyResponse.put("residents", List.of());

        when(fireInfoService.getResidentsByAddress(address)).thenReturn(emptyResponse);

        mockMvc.perform(get("/fire").param("address", address))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.fireStationNumber").doesNotExist())
               .andExpect(jsonPath("$.residents").isEmpty());
    }
}

