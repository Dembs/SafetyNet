package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @MockBean
    private FireStationRepository fireStationRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllFireStationsTest() throws Exception {
        List<FireStation> fireStations = List.of(new FireStation("1509 Culver St", "3"));
        when(fireStationRepository.findAll()).thenReturn(fireStations);

        mockMvc.perform(get("/firestation"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()").value(1))
               .andExpect(jsonPath("$[0].address").value("1509 Culver St"))
               .andExpect(jsonPath("$[0].station").value("3"));

        verify(fireStationRepository, times(1)).findAll();
    }

    @Test
    void addOneFireStationTest() throws Exception {

        FireStation fireStation = new FireStation("1509 Culver St", "3");
        doNothing().when(fireStationRepository).save(fireStation);

        mockMvc.perform(post("/firestation")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(fireStation)))
               .andExpect(status().isOk())
               .andExpect(content().string("Fire Station added successfully!"));

        verify(fireStationRepository, times(1)).save(Mockito.any(FireStation.class));
    }

    @Test
    void addOneFireStationTest_Exception() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "1");
        doThrow(new RuntimeException("Save operation failed")).when(fireStationRepository).save(Mockito.any(FireStation.class));

        mockMvc.perform(post("/firestation")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(fireStation)))
               .andExpect(status().isOk())
               .andExpect(content().string("Failed to add fire station."));

        verify(fireStationRepository, times(1)).save(Mockito.any(FireStation.class));
    }

    @Test
    void deleteOneFireStationTest() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.of(fireStation));
        doNothing().when(fireStationRepository).delete(fireStation);

        mockMvc.perform(delete("/firestation")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(fireStation)))
               .andExpect(status().isOk())
               .andExpect(content().json("{}"));

        verify(fireStationRepository, times(1)).delete(fireStation);
        verify(fireStationRepository, times(1)).findByAddress(fireStation.getAddress());
    }

    @Test
    void deleteOneFireStationTest_StationNotFound() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/firestation")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(fireStation)))
               .andExpect(status().isOk())
               .andExpect(content().json("{}"));

        verify(fireStationRepository, never()).delete(any(FireStation.class));
        verify(fireStationRepository, times(1)).findByAddress(fireStation.getAddress());
    }

    @Test
    void deleteOneFireStationTest_Exception() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.of(fireStation));
        doThrow(new RuntimeException("Database error")).when(fireStationRepository).delete(any(FireStation.class));

        mockMvc.perform(delete("/firestation")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(fireStation)))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Failed to delete fire station."));

        verify(fireStationRepository, times(1)).delete(any(FireStation.class));
        verify(fireStationRepository, times(1)).findByAddress(fireStation.getAddress());
    }

    @Test
    void updateOneFireStationTest() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.of(fireStation));
        doNothing().when(fireStationRepository).update(fireStation);

        mockMvc.perform(put("/firestation")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(fireStation)))
               .andExpect(status().isOk())
               .andExpect(content().json("{}"));

        verify(fireStationRepository, times(1)).update(fireStation);
        verify(fireStationRepository, times(1)).findByAddress(fireStation.getAddress());
    }

    @Test
    void updateOneFireStationTest_StationNotFound() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.empty());

        mockMvc.perform(put("/firestation")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(fireStation)))
               .andExpect(status().isOk())
               .andExpect(content().json("{}"));

        verify(fireStationRepository, never()).update(any(FireStation.class));
        verify(fireStationRepository, times(1)).findByAddress(fireStation.getAddress());
    }

    @Test
    void updateOneFireStationTest_Exception() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(Optional.of(fireStation));
        doThrow(new RuntimeException("Database error")).when(fireStationRepository).update(any(FireStation.class));

        mockMvc.perform(put("/firestation")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(fireStation)))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Failed to update fire station."));

        verify(fireStationRepository, times(1)).update(any(FireStation.class));
        verify(fireStationRepository, times(1)).findByAddress(fireStation.getAddress());
    }

    @Test
    void getPersonsByStationTest() throws Exception {
        String stationNumber = "1";
        Map<String, Object> response = Map.of(
                "adults", 2,
                "children", 1,
                "persons", List.of(
                        Map.of("firstName", "John", "lastName", "Boyd", "address", "1509 Culver St", "phone", "841-874-6512"),
                        Map.of("firstName", "Jane", "lastName", "Doe", "address", "1509 Culver St", "phone", "841-874-6513")
                )
        );
        when(fireStationService.getPersonsByStation(stationNumber)).thenReturn(response);

        mockMvc.perform(get("/firestation")
                       .param("stationNumber", stationNumber))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.adults").value(2))
               .andExpect(jsonPath("$.children").value(1))
               .andExpect(jsonPath("$.persons.length()").value(2))
               .andExpect(jsonPath("$.persons[0].firstName").value("John"))
               .andExpect(jsonPath("$.persons[1].firstName").value("Jane"));

        verify(fireStationService, times(1)).getPersonsByStation(stationNumber);
    }

}
