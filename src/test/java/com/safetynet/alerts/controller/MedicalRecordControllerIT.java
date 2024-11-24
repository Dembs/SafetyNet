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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MedicalRecordControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getAllMedicalRecordsIT() throws Exception {
        mockMvc.perform(get("/medicalRecord")
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", not(empty())))
               .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @Test
    void addOneMedicalRecordIT() throws Exception {
        String medicalRecordJson = """
                {
                    "firstName": "TestFirstName",
                    "lastName": "TestLastName",
                    "birthdate": "01/01/2000",
                    "medications": ["med1:500mg", "med2:250mg"],
                    "allergies": ["allergy1", "allergy2"]
                }
                """;

        mockMvc.perform(post("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(medicalRecordJson))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("Medical Record added successfully")));
    }

    @Test
    void updateOneMedicalRecordIT() throws Exception {
        String updatedMedicalRecordJson = """
                {
                    "firstName": "UpdatedFirstName",
                    "lastName": "UpdatedLastName",
                    "birthdate": "02/02/1990",
                    "medications": ["updatedMed1:300mg"],
                    "allergies": ["updatedAllergy1"]
                }
                """;

        mockMvc.perform(put("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(updatedMedicalRecordJson))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("MedicalRecord updated successfully")));
    }

    @Test
    void deleteOneMedicalRecordIT() throws Exception {
        String medicalRecordToDeleteJson = """
                {
                    "firstName": "DeleteFirstName",
                    "lastName": "DeleteLastName",
                    "birthdate": "03/03/1980",
                    "medications": ["deleteMed1:100mg"],
                    "allergies": ["deleteAllergy1"]
                }
                """;

        mockMvc.perform(delete("/medicalRecord")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(medicalRecordToDeleteJson))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("MedicalRecord deleted successfully")));
    }
}
