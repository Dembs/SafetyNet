package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalRecordController.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordRepository medicalRecordRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllMedicalRecords_ShouldReturnListOfMedicalRecords() throws Exception {

        List<MedicalRecord> medicalRecords = List.of(
                new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1", "med2"), List.of("allergy1")),
                new MedicalRecord("Jane", "Smith", "02/02/1985", List.of("med3"), List.of("allergy2"))
        );
        when(medicalRecordRepository.findAll()).thenReturn(medicalRecords);

        mockMvc.perform(get("/medicalRecord"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].firstName").value("John"))
               .andExpect(jsonPath("$[1].firstName").value("Jane"));

        verify(medicalRecordRepository, times(1)).findAll();
    }

    @Test
    void addOneMedicalRecord() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1", "med2"), List.of("allergy1"));
        doNothing().when(medicalRecordRepository).save(medicalRecord);

        mockMvc.perform(post("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(medicalRecord)))
               .andExpect(status().isOk())
               .andExpect(content().string("Medical Record added successfully"));

        verify(medicalRecordRepository, times(1)).save(any(MedicalRecord.class));
    }

    @Test
    void deleteOneMedicalRecord() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1", "med2"), List.of("allergy1"));
        doNothing().when(medicalRecordRepository).delete(medicalRecord);

        mockMvc.perform(delete("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(medicalRecord)))
               .andExpect(status().isOk())
               .andExpect(content().string("MedicalRecord deleted successfully"));

        verify(medicalRecordRepository, times(1)).delete(any(MedicalRecord.class));
    }

    @Test
    void updateOneMedicalRecord() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1", "med2"), List.of("allergy1"));
        doNothing().when(medicalRecordRepository).update(medicalRecord);

        mockMvc.perform(put("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(medicalRecord)))
               .andExpect(status().isOk())
               .andExpect(content().string("MedicalRecord updated successfully"));

        verify(medicalRecordRepository, times(1)).update(any(MedicalRecord.class));
    }
}
