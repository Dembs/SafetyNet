package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ResidentInfoDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FireInfoServiceTest {

    @Mock
    private FireStationRepository fireStationRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private FireInfoService fireInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getResidentsByAddress() {
        String address = "1509 Culver St";
        String fireStationNumber = "1";

        FireStation fireStation = new FireStation(address, fireStationNumber);
        Person person1 = new Person("John", "Doe", "1509 Culver St", "Culver", 97451, "841-874-6512", "john.boyd@example.com");
        Person person2 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6513", "jacob.boyd@example.com");


        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Doe", "03/06/1984", List.of("med1:500mg"), List.of("allergy1"));
        MedicalRecord medicalRecord2 = new MedicalRecord("Jacob", "Boyd", "05/10/2010", List.of("med2:250mg"), List.of("allergy2"));

        when(fireStationRepository.findAll()).thenReturn(List.of(fireStation));
        when(personRepository.findPersonsByAddress(address)).thenReturn(List.of(person1, person2));
        when(medicalRecordRepository.findMedicalRecord("John", "Doe")).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordRepository.findMedicalRecord("Jacob", "Boyd")).thenReturn(Optional.of(medicalRecord2));

        Map<String, Object> result = fireInfoService.getResidentsByAddress(address);

        assertEquals(fireStationNumber, result.get("fireStationNumber"));

        List<ResidentInfoDTO> residents = (List<ResidentInfoDTO>) result.get("residents");
        assertEquals(2, residents.size());

        ResidentInfoDTO resident1 = residents.get(0);
        assertEquals("John", resident1.getFirstName());
        assertEquals("Doe", resident1.getLastName());
        assertEquals("841-874-6512", resident1.getPhone());
        assertEquals(40, resident1.getAge());
        assertEquals(List.of("med1:500mg"), resident1.getMedications());
        assertEquals(List.of("allergy1"), resident1.getAllergies());

        ResidentInfoDTO resident2 = residents.get(1);
        assertEquals("Jacob", resident2.getFirstName());
        assertEquals("Boyd", resident2.getLastName());
        assertEquals("841-874-6513", resident2.getPhone());
        assertEquals(14, resident2.getAge());
        assertEquals(List.of("med2:250mg"), resident2.getMedications());
        assertEquals(List.of("allergy2"), resident2.getAllergies());

        verify(fireStationRepository, times(1)).findAll();
        verify(personRepository, times(1)).findPersonsByAddress(address);
        verify(medicalRecordRepository, times(1)).findMedicalRecord("John", "Doe");
        verify(medicalRecordRepository, times(1)).findMedicalRecord("Jacob", "Boyd");
    }

}
