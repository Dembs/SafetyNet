package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ResidentInfoDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class FloodServiceTest {

    @Mock
    private FireStationRepository fireStationRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private FloodService floodService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getHouseholdsByStationsTest() {

        List<String> stationNumbers = List.of("1", "2");

        Set<String> addressesForStation1 = Set.of("1509 Culver St");
        Set<String> addressesForStation2 = Set.of("29 13th St");
        when(fireStationRepository.findAddressByStationNumber("1")).thenReturn(Optional.of(new ArrayList<>(addressesForStation1)));
        when(fireStationRepository.findAddressByStationNumber("2")).thenReturn(Optional.of(new ArrayList<>(addressesForStation2)));

        Person person1 = new Person("John", "Doe", "1509 Culver St", "Culver", 97451, "841-874-6512", "john.doe@example.com");
        Person person2 = new Person("Jane", "Smith", "29 13th St", "Culver", 97451, "841-874-6513", "jane.smith@example.com");

        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Doe", "03/06/1984", List.of("med1"), List.of("allergy1"));
        MedicalRecord medicalRecord2 = new MedicalRecord("Jane", "Smith", "05/10/2010", List.of("med2"), List.of("allergy2"));

        when(personRepository.findPersonsByAddress("1509 Culver St")).thenReturn(List.of(person1));
        when(personRepository.findPersonsByAddress("29 13th St")).thenReturn(List.of(person2));

        when(medicalRecordRepository.findMedicalRecord("John", "Doe")).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordRepository.findMedicalRecord("Jane", "Smith")).thenReturn(Optional.of(medicalRecord2));

        Map<String, List<ResidentInfoDTO>> result = floodService.getHouseholdsByStations(stationNumbers);

        assertEquals(2, result.size());
        assertTrue(result.containsKey("1509 Culver St"));
        assertTrue(result.containsKey("29 13th St"));

        ResidentInfoDTO resident1 = result.get("1509 Culver St").get(0);
        assertEquals("John", resident1.getFirstName());
        assertEquals("Doe", resident1.getLastName());
        assertEquals(40, resident1.getAge());
        assertEquals(List.of("med1"), resident1.getMedications());
        assertEquals(List.of("allergy1"), resident1.getAllergies());

        ResidentInfoDTO resident2 = result.get("29 13th St").get(0);
        assertEquals("Jane", resident2.getFirstName());
        assertEquals("Smith", resident2.getLastName());
        assertEquals(14, resident2.getAge());
        assertEquals(List.of("med2"), resident2.getMedications());
        assertEquals(List.of("allergy2"), resident2.getAllergies());

        verify(fireStationRepository, times(1)).findAddressByStationNumber("1");
        verify(fireStationRepository, times(1)).findAddressByStationNumber("2");
        verify(personRepository, times(1)).findPersonsByAddress("1509 Culver St");
        verify(personRepository, times(1)).findPersonsByAddress("29 13th St");
        verify(medicalRecordRepository, times(1)).findMedicalRecord("John", "Doe");
        verify(medicalRecordRepository, times(1)).findMedicalRecord("Jane", "Smith");
    }

}
