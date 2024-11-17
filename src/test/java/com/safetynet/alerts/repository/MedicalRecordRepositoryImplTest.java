package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalRecordRepositoryImplTest {

    @Mock
    private DataLoader dataLoader;

    private MedicalRecordRepositoryImpl medicalRecordRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simuler une liste de MedicalRecords fictifs
        Data mockData = new Data();
        List<MedicalRecord> mockMedicalRecords = new ArrayList<>();
        mockMedicalRecords.add(new MedicalRecord("Jane", "Doe", "01/01/1980", List.of("med1"), List.of("allergy1")));
        mockMedicalRecords.add(new MedicalRecord("John", "Smith", "02/02/1990", List.of("med2"), List.of("allergy2")));

        mockData.setMedicalrecords(mockMedicalRecords);
        // Configurer le DataLoader pour retourner l'objet Data simulé

        when(dataLoader.getData()).thenReturn(mockData);

        // Initialiser le repository avec le DataLoader mocké
        medicalRecordRepository = new MedicalRecordRepositoryImpl(dataLoader);
    }

    @Test
    public void saveAndFindTest() {
        // Nouveaux MedicalRecords
        List<String> medications = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        List<String> allergies = Arrays.asList("nillacilan");
        MedicalRecord medicalRecord = new MedicalRecord("Demba", "SY", "03/06/1984", medications, allergies);

        medicalRecordRepository.save(medicalRecord);


        List<MedicalRecord> foundAll = medicalRecordRepository.findAll();
        Optional<MedicalRecord> foundMedicalRecord = medicalRecordRepository.findMedicalRecord("Demba", "SY");

        assertEquals(3, foundAll.size()); // Les 2 mockés + les 2 ajoutés
        assertEquals("John", foundAll.get(1).getFirstName());
        assertTrue(foundMedicalRecord.isPresent());
        assertEquals("Demba", foundMedicalRecord.get().getFirstName());
        assertEquals("SY", foundMedicalRecord.get().getLastName());
    }

    @Test
    public void deleteTest() {
        // Ajouter un MedicalRecord
        List<String> medications = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        List<String> allergies = Arrays.asList("nillacilan");
        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd", "03/06/1984", medications, allergies);

        medicalRecordRepository.save(medicalRecord);
        medicalRecordRepository.delete(medicalRecord);

        Optional<MedicalRecord> foundMedicalRecord = medicalRecordRepository.findMedicalRecord("John", "Boyd");

        assertFalse(foundMedicalRecord.isPresent());
    }

    @Test
    public void updateMedicalRecordTest() {
        // Ajouter un MedicalRecord
        List<String> medications = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        List<String> allergies = Arrays.asList("nillacilan");
        MedicalRecord medicalRecord = new MedicalRecord("John", "Boyd", "03/06/1984", medications, allergies);

        medicalRecordRepository.save(medicalRecord);

        // Mettre à jour le MedicalRecord
        List<String> updatedMedications = Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg");
        List<String> updatedAllergies = Arrays.asList("lactose");
        MedicalRecord updatedMedicalRecord = new MedicalRecord("John", "Boyd", "03/06/1989", updatedMedications, updatedAllergies);

        medicalRecordRepository.update(updatedMedicalRecord);

        Optional<MedicalRecord> retrievedMedicalRecord = medicalRecordRepository.findMedicalRecord("John", "Boyd");

        assertTrue(retrievedMedicalRecord.isPresent());
        assertEquals("03/06/1989", retrievedMedicalRecord.get().getBirthdate());
        assertEquals(updatedMedications, retrievedMedicalRecord.get().getMedications());
        assertEquals(updatedAllergies, retrievedMedicalRecord.get().getAllergies());
    }
}
