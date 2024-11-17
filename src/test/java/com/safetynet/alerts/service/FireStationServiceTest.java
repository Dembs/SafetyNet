package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoDTO;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FireStationServiceTest {

    @Mock
    private FireStationRepository fireStationRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private FireStationService fireStationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPersonsByStation() {
        // Mock des données
        String stationNumber = "1";
        List<String> addresses = List.of("1509 Culver St");
        List<Person> persons = List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "john.boyd@example.com"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6513", "jacob.boyd@example.com")
        );

        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Boyd", "03/06/1984", List.of(), List.of());
        MedicalRecord medicalRecord2 = new MedicalRecord("Jacob", "Boyd", "03/06/2010", List.of(), List.of());

        // Mock des appels
        when(fireStationRepository.findAddressByStationNumber(stationNumber)).thenReturn(Optional.of(addresses));
        when(personRepository.findPersonsByAddress("1509 Culver St")).thenReturn(persons);
        when(medicalRecordRepository.findMedicalRecord("John", "Boyd")).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordRepository.findMedicalRecord("Jacob", "Boyd")).thenReturn(Optional.of(medicalRecord2));

        // Exécution
        Map<String, Object> result = fireStationService.getPersonsByStation(stationNumber);

        // Vérifications
        assertEquals(1, result.get("adults"));
        assertEquals(1, result.get("children"));

        @SuppressWarnings("unchecked")
        List<PersonInfoDTO> personsInfo = (List<PersonInfoDTO>) result.get("persons");
        assertEquals(2, personsInfo.size());
        assertEquals("John", personsInfo.get(0).getFirstName());
        assertEquals("Jacob", personsInfo.get(1).getFirstName());

        // Vérifier les interactions
        verify(fireStationRepository, times(1)).findAddressByStationNumber(stationNumber);
        verify(personRepository, times(1)).findPersonsByAddress("1509 Culver St");
        verify(medicalRecordRepository, times(1)).findMedicalRecord("John", "Boyd");
        verify(medicalRecordRepository, times(1)).findMedicalRecord("Jacob", "Boyd");
    }
}
