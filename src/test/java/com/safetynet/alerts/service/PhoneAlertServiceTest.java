package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
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

class PhoneAlertServiceTest {

    @Mock
    private FireStationRepository fireStationRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PhoneAlertService phoneAlertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPhoneNumbersByFireStation() {

        String fireStationNumber = "1";
        List<String> addresses = List.of("1509 Culver St", "29 13th Ave");
        List<Person> personsAtFirstAddress = Arrays.asList(
                new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "john.boyd@example.com"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6513", "jacob.boyd@example.com")
        );
        List<Person> personsAtSecondAddress = List.of(
                new Person("Emily", "Smith", "29 13th Ave", "Culver", 44880, "123-456-7899", "Emily.smith@example.com")
        );

        when(fireStationRepository.findAddressByStationNumber(fireStationNumber)).thenReturn(Optional.of(addresses));
        when(personRepository.findPersonsByAddress("1509 Culver St")).thenReturn(personsAtFirstAddress);
        when(personRepository.findPersonsByAddress("29 13th Ave")).thenReturn(personsAtSecondAddress);

        List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFireStation(fireStationNumber);

        assertEquals(3, phoneNumbers.size());
        assertEquals("841-874-6512", phoneNumbers.get(0));
        assertEquals("841-874-6513", phoneNumbers.get(1));
        assertEquals("123-456-7899", phoneNumbers.get(2));

        verify(fireStationRepository, times(1)).findAddressByStationNumber(fireStationNumber);
        verify(personRepository, times(1)).findPersonsByAddress("1509 Culver St");
        verify(personRepository, times(1)).findPersonsByAddress("29 13th Ave");
    }

}
