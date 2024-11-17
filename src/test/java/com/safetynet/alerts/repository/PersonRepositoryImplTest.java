package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonRepositoryImplTest {

    @Mock
    private DataLoader dataLoader;

    private PersonRepositoryImpl personRepository;

    @BeforeEach
    public void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);

        // Mock de Data
        Data mockData = new Data();
        List<Person> mockPersons = Arrays.asList(
                new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"),
                new Person("Jane", "Doe", "1509 Culver St", "Culver", 97451, "841-874-1234", "jdoe@email.com")
        );
        mockData.setPersons(mockPersons);

        when(dataLoader.getData()).thenReturn(mockData);

        personRepository = new PersonRepositoryImpl(dataLoader);
    }

    @Test
    public void saveAndFindPersonTest() {

        Person person = new Person("Jacob", "Boyd", "123 Main St", "Springfield", 12345, "123-456-7890", "jacob@email.com");
        personRepository.save(person);

        List<Person> foundAll = personRepository.findAll();
        assertEquals(3, foundAll.size());
        assertTrue(foundAll.stream().anyMatch(p -> p.getFirstName().equals("Jacob")));


        Optional<Person> foundPerson = personRepository.findPerson("Jacob", "Boyd");
        assertTrue(foundPerson.isPresent());
        assertEquals("Jacob", foundPerson.get().getFirstName());
        assertEquals("Boyd", foundPerson.get().getLastName());
    }

    @Test
    public void deletePersonTest() {

        Person person = new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
        personRepository.delete(person);

        Optional<Person> foundPerson = personRepository.findPerson("John", "Boyd");
        assertFalse(foundPerson.isPresent());
    }

    @Test
    public void updatePersonTest() {

        Person updatedPerson = new Person("John", "Boyd", "23 avenue", "Nantes", 44000, "841-874-6512", "jaboyd@email.com");
        personRepository.update(updatedPerson);


        Optional<Person> retrievedPerson = personRepository.findPerson("John", "Boyd");

        assertTrue(retrievedPerson.isPresent());
        assertEquals("23 avenue", retrievedPerson.get().getAddress());
        assertEquals("Nantes", retrievedPerson.get().getCity());
        assertEquals(44000, retrievedPerson.get().getZip());
        assertEquals("841-874-6512", retrievedPerson.get().getPhone());
        assertEquals("jaboyd@email.com", retrievedPerson.get().getEmail());
    }

    @Test
    public void findPersonsByAddressTest() {

        List<Person> personsAtAddress = personRepository.findPersonsByAddress("1509 Culver St");
        assertEquals(2, personsAtAddress.size());
        assertTrue(personsAtAddress.stream().anyMatch(person -> person.getFirstName().equals("John")));
        assertTrue(personsAtAddress.stream().anyMatch(person -> person.getFirstName().equals("Jane")));
    }
}
