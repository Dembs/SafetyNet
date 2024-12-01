package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PersonInfoServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private DateService dateService;

    @InjectMocks
    private PersonInfoService personInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPersonsByLastNameTest() {

        String lastName = "Doe";

        Person person1 = new Person("John", "Doe", "123 Main St", "Anytown", 12345, "123-456-7890", "john.doe@example.com");
        Person person2 = new Person("Jane", "Doe", "123 Main St", "Anytown", 12345, "123-456-7891", "jane.doe@example.com");

        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Doe", "03/06/1984", List.of("med1:500mg"), List.of("allergy1"));
        MedicalRecord medicalRecord2 = new MedicalRecord("Jane", "Doe", "05/10/2010", List.of("med2:250mg"), List.of("allergy2"));

        when(personRepository.findPersonsByLastName(lastName)).thenReturn(List.of(person1, person2));
        when(medicalRecordRepository.findMedicalRecord("John", "Doe")).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordRepository.findMedicalRecord("Jane", "Doe")).thenReturn(Optional.of(medicalRecord2));
        when(dateService.calculateAge("03/06/1984")).thenReturn(40);
        when(dateService.calculateAge("05/10/2010")).thenReturn(14);

        List<PersonInfoDTO> result = personInfoService.getPersonsByLastName(lastName);

        assertEquals(2, result.size());

        PersonInfoDTO personInfo1 = result.get(0);
        assertEquals("John", personInfo1.getFirstName());
        assertEquals("Doe", personInfo1.getLastName());
        assertEquals("123 Main St", personInfo1.getAddress());
        assertEquals("john.doe@example.com", personInfo1.getEmail());
        assertEquals(40, personInfo1.getAge());
        assertEquals(List.of("med1:500mg"), personInfo1.getMedications());
        assertEquals(List.of("allergy1"), personInfo1.getAllergies());

        PersonInfoDTO personInfo2 = result.get(1);
        assertEquals("Jane", personInfo2.getFirstName());
        assertEquals("Doe", personInfo2.getLastName());
        assertEquals("123 Main St", personInfo2.getAddress());
        assertEquals("jane.doe@example.com", personInfo2.getEmail());
        assertEquals(14, personInfo2.getAge());
        assertEquals(List.of("med2:250mg"), personInfo2.getMedications());
        assertEquals(List.of("allergy2"), personInfo2.getAllergies());

        verify(personRepository, times(1)).findPersonsByLastName(lastName);
        verify(medicalRecordRepository, times(1)).findMedicalRecord("John", "Doe");
        verify(medicalRecordRepository, times(1)).findMedicalRecord("Jane", "Doe");
    }

}
