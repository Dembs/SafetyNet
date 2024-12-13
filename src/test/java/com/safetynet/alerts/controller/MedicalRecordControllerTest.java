package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

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
    void getAllMedicalRecordsTest() throws Exception {

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
    void addOneMedicalRecordTest() throws Exception {

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
    void addOneMedicalRecordTest_Exception() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord("Jane", "Smith", "02/02/1990", List.of("med2"), List.of("allergy2"));
        doThrow(new RuntimeException("Save operation failed")).when(medicalRecordRepository).save(Mockito.any(MedicalRecord.class));

        mockMvc.perform(post("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(medicalRecord)))
               .andExpect(status().isOk())
               .andExpect(content().string("Failed to add medical record."));

        verify(medicalRecordRepository, times(1)).save(Mockito.any(MedicalRecord.class));
    }

    @Test
    void deleteOneMedicalRecordTest() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1", "med2"), List.of("allergy1"));
        when(medicalRecordRepository.findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName()))
            .thenReturn(Optional.of(medicalRecord));
        doNothing().when(medicalRecordRepository).delete(medicalRecord);

        mockMvc.perform(delete("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(medicalRecord)))
               .andExpect(status().isOk())
               .andExpect(content().json("{}"));

        verify(medicalRecordRepository, times(1)).delete(medicalRecord);
        verify(medicalRecordRepository, times(1)).findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());
    }

    @Test
    void deleteOneMedicalRecordTest_RecordNotFound() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1", "med2"), List.of("allergy1"));
        when(medicalRecordRepository.findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName()))
            .thenReturn(Optional.empty());

        mockMvc.perform(delete("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(medicalRecord)))
               .andExpect(status().isOk())
               .andExpect(content().json("{}"));

        verify(medicalRecordRepository, never()).delete(any(MedicalRecord.class));
        verify(medicalRecordRepository, times(1)).findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());
    }

    @Test
    void deleteOneMedicalRecordTest_Exception() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1", "med2"), List.of("allergy1"));
        when(medicalRecordRepository.findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName()))
            .thenReturn(Optional.of(medicalRecord));
        doThrow(new RuntimeException("Database error")).when(medicalRecordRepository).delete(any(MedicalRecord.class));

        mockMvc.perform(delete("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(medicalRecord)))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Failed to delete medical record."));

        verify(medicalRecordRepository, times(1)).delete(any(MedicalRecord.class));
        verify(medicalRecordRepository, times(1)).findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());
    }

    @Test
    void updateOneMedicalRecordTest() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1", "med2"), List.of("allergy1"));
        when(medicalRecordRepository.findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName()))
            .thenReturn(Optional.of(medicalRecord));
        doNothing().when(medicalRecordRepository).update(medicalRecord);

        mockMvc.perform(put("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(medicalRecord)))
               .andExpect(status().isOk())
               .andExpect(content().json("{}"));

        verify(medicalRecordRepository, times(1)).update(medicalRecord);
        verify(medicalRecordRepository, times(1)).findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());
    }

    @Test
    void updateOneMedicalRecordTest_RecordNotFound() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1", "med2"), List.of("allergy1"));
        when(medicalRecordRepository.findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName()))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(medicalRecord)))
               .andExpect(status().isOk())
               .andExpect(content().json("{}"));

        verify(medicalRecordRepository, never()).update(any(MedicalRecord.class));
        verify(medicalRecordRepository, times(1)).findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());
    }

    @Test
    void updateOneMedicalRecordTest_Exception() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1", "med2"), List.of("allergy1"));
        when(medicalRecordRepository.findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName()))
            .thenReturn(Optional.of(medicalRecord));
        doThrow(new RuntimeException("Database error")).when(medicalRecordRepository).update(any(MedicalRecord.class));

        mockMvc.perform(put("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(medicalRecord)))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Failed to update medical record."));

        verify(medicalRecordRepository, times(1)).update(any(MedicalRecord.class));
        verify(medicalRecordRepository, times(1)).findMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());
    }
}
