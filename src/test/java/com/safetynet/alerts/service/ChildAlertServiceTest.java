package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildInfoDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChildAlertServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private DateService dateService;

    @InjectMocks
    private ChildAlertService childAlertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getChildrenAtAddressTest() {
        String address = "1509 Culver St";
        List<Person> personsAtAddress = Arrays.asList(
                new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "john.boyd@example.com"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6513", "jacob.boyd@example.com")
        );

        MedicalRecord johnMedicalRecord = new MedicalRecord("John", "Boyd", "03/06/1984", List.of(), List.of());
        MedicalRecord jacobMedicalRecord = new MedicalRecord("Jacob", "Boyd", "03/06/2010", List.of(), List.of());


        when(personRepository.findPersonsByAddress(address)).thenReturn(personsAtAddress);
        when(medicalRecordRepository.findMedicalRecord("John", "Boyd")).thenReturn(Optional.of(johnMedicalRecord));
        when(medicalRecordRepository.findMedicalRecord("Jacob", "Boyd")).thenReturn(Optional.of(jacobMedicalRecord));
        when(dateService.calculateAge("03/06/1984")).thenReturn(39);
        when(dateService.calculateAge("03/06/2010")).thenReturn(14);

        List<ChildInfoDTO> children = childAlertService.getChildrenAtAddress(address);

        assertEquals(1, children.size());

        ChildInfoDTO jacobInfo = children.get(0);
        assertEquals("Jacob", jacobInfo.getFirstName());
        assertEquals("Boyd", jacobInfo.getLastName());
        assertEquals(14, jacobInfo.getAge());
        assertEquals(1, jacobInfo.getFamilyMembers().size());

    }


}
