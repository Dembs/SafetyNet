package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommunityEmailServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private CommunityEmailService communityEmailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEmailsByCityTest() {
        String city = "Springfield";
        List<Person> mockPersons = Arrays.asList(
                new Person("John", "Doe", "123 Main St", city, 12345, "123-456-7890", "john.doe@example.com"),
                new Person("Jane", "Smith", "456 Elm St", city, 12345, "987-654-3210", "jane.smith@example.com"),
                new Person("Alice", "Brown", "789 Maple Ave", city, 12345, "456-789-1230", "john.doe@example.com") // Duplicate email
        );

        when(personRepository.findPersonsByCity(city)).thenReturn(mockPersons);

        List<String> result = communityEmailService.getEmailsByCity(city);

        assertEquals(2, result.size());
        assertEquals(List.of("john.doe@example.com", "jane.smith@example.com"), result);

        verify(personRepository, times(1)).findPersonsByCity(city);
    }
}
