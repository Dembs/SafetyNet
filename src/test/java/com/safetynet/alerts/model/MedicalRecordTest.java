package com.safetynet.alerts.model;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicalRecordTest {
    private static MedicalRecord medicalRecord;

    @Test
    public void getMedicalRecordTest(){
        List<String> medications = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        List<String> allergies = Arrays.asList("nillacilan");
        medicalRecord = new MedicalRecord("John","Boyd", "03/06/1984",medications,allergies);

        assertEquals("John",medicalRecord.getFirstName());
        assertEquals("Boyd",medicalRecord.getLastName());
        assertEquals("03/06/1984",medicalRecord.getBirthdate());
        assertEquals(medications,medicalRecord.getMedications());
        assertEquals(allergies,medicalRecord.getAllergies());
    }
}
