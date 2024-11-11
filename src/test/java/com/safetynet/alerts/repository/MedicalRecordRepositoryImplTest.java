package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordRepositoryImplTest {

    private MedicalRecordRepositoryImpl medicalRecordRepository;
    private DataLoader dataLoader;

    @BeforeEach
    public void setUp(){
        medicalRecordRepository = new MedicalRecordRepositoryImpl(dataLoader);
    }

    @Test
    public void saveAndFindTest() {
        List<String> medications1 = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        List<String> allergies1 = Arrays.asList("nillacilan");
        MedicalRecord medicalRecord1 = new MedicalRecord("John","Boyd", "03/06/1984",medications1,allergies1);

        List<String> medications2 = Arrays.asList("pharmacol:5000mg", "terazine:10mg","noznazol:250mg");
        List<String> allergies2 = Arrays.asList("");
        MedicalRecord medicalRecord2 = new MedicalRecord("Jacob","Boyd", "03/06/1989",medications2,allergies2);

        medicalRecordRepository.save(medicalRecord1);
        medicalRecordRepository.save(medicalRecord2);

        List<MedicalRecord>foundAll = medicalRecordRepository.findAll();
        Optional<MedicalRecord>foundMedicalRecord = medicalRecordRepository.findMedicalRecord("John","Boyd");

        assertEquals(2,foundAll.size());
        assertEquals("Jacob",foundAll.get(1).getFirstName());
        assertTrue(foundMedicalRecord.isPresent());
        assertEquals("John",foundMedicalRecord.get().getFirstName());
        assertEquals("Boyd",foundMedicalRecord.get().getLastName());
    }


    @Test
    public void deleteTest() {
        List<String> medications = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        List<String> allergies = Arrays.asList("nillacilan");
        MedicalRecord medicalRecord = new MedicalRecord("John","Boyd", "03/06/1984",medications,allergies);

        medicalRecordRepository.save(medicalRecord);
        medicalRecordRepository.delete(medicalRecord);

        Optional<MedicalRecord>foundMedicalRecord = medicalRecordRepository.findMedicalRecord("John","Boyd");

        assertFalse(foundMedicalRecord.isPresent());
    }

    @Test
    public void updateMedicalRecordTest() {
        List<String> medications = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        List<String> allergies = Arrays.asList("nillacilan");
        MedicalRecord medicalRecord = new MedicalRecord("John","Boyd", "03/06/1984",medications,allergies);

        medicalRecordRepository.save(medicalRecord);

        List<String> updatedMedications = Arrays.asList("pharmacol:5000mg", "terazine:10mg","noznazol:250mg");
        List<String> updatedAllergies = Arrays.asList("lactose");
        MedicalRecord updatedMedicalRecord = new MedicalRecord("John","Boyd", "03/06/1989",updatedMedications,updatedAllergies);

        medicalRecordRepository.update(updatedMedicalRecord);

        Optional<MedicalRecord>retrievedMedicalRecord = medicalRecordRepository.findMedicalRecord("John","Boyd");

        assertTrue(retrievedMedicalRecord.isPresent());
        assertEquals("03/06/1989", retrievedMedicalRecord.get().getBirthdate());
        assertEquals(Arrays.asList("pharmacol:5000mg", "terazine:10mg","noznazol:250mg"), retrievedMedicalRecord.get().getMedications());
        assertEquals(Arrays.asList("lactose"), retrievedMedicalRecord.get().getAllergies());

    }
}