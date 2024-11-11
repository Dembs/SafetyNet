package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PersonRepositoryImplTest {

    private PersonRepositoryImpl personRepository;
    private DataLoader dataLoader;

    @BeforeEach
    public void setUp() {
        personRepository = new PersonRepositoryImpl(dataLoader);
    }

    @Test
    public void saveAndFindPersonTest(){
        Person person1 = new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
        personRepository.save(person1);
        Person person2 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "drk@email.com");
        personRepository.save(person2);

        List<Person> foundAll = personRepository.findAll();
        Optional<Person>foundPerson = personRepository.findPerson("John","Boyd");

        assertEquals(foundAll.size(),2);
        assertEquals("Jacob",foundAll.get(1).getFirstName());
        assertTrue(foundPerson.isPresent());
        assertEquals("John",foundPerson.get().getFirstName());
        assertEquals("Boyd",foundPerson.get().getLastName());
    }

    @Test
    public void deletePersonTest(){
        Person person = new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
        personRepository.save(person);
        personRepository.delete(person);

        Optional<Person>foundPerson = personRepository.findPerson("John","Boyd");
        assertFalse(foundPerson.isPresent());
    }
    @Test
    public void updatePersonTest() {

        Person person = new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
        personRepository.save(person);


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

}