package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonInfoDTOTest {

    @Test
    void testConstructorAndGetters() {

        PersonInfoDTO personInfo = new PersonInfoDTO(
                "John",
                "Doe",
                "123 Main St",
                "john.doe@example.com",
                30,
                List.of("med1", "med2"),
                List.of("allergy1")
        );

        assertEquals("John", personInfo.getFirstName());
        assertEquals("Doe", personInfo.getLastName());
        assertEquals("123 Main St", personInfo.getAddress());
        assertEquals("john.doe@example.com", personInfo.getEmail());
        assertEquals(30, personInfo.getAge());
        assertEquals(2, personInfo.getMedications().size());
        assertEquals("med1", personInfo.getMedications().get(0));
        assertEquals(1, personInfo.getAllergies().size());
        assertEquals("allergy1", personInfo.getAllergies().get(0));
    }

    @Test
    void testSetters() {

        PersonInfoDTO personInfo = new PersonInfoDTO(null, null, null, null, 0, null, null);

        personInfo.setFirstName("Jane");
        personInfo.setLastName("Smith");
        personInfo.setAddress("456 Elm St");
        personInfo.setEmail("jane.smith@example.com");
        personInfo.setAge(25);
        personInfo.setMedications(List.of("medA", "medB"));
        personInfo.setAllergies(List.of("allergyX"));

        assertEquals("Jane", personInfo.getFirstName());
        assertEquals("Smith", personInfo.getLastName());
        assertEquals("456 Elm St", personInfo.getAddress());
        assertEquals("jane.smith@example.com", personInfo.getEmail());
        assertEquals(25, personInfo.getAge());
        assertEquals(2, personInfo.getMedications().size());
        assertEquals("medA", personInfo.getMedications().get(0));
        assertEquals(1, personInfo.getAllergies().size());
        assertEquals("allergyX", personInfo.getAllergies().get(0));
    }

    @Test
    void testEmptyValues() {

        PersonInfoDTO personInfo = new PersonInfoDTO("", "", "", "", 0, List.of(), List.of());

        assertEquals("", personInfo.getFirstName());
        assertEquals("", personInfo.getLastName());
        assertEquals("", personInfo.getAddress());
        assertEquals("", personInfo.getEmail());
        assertEquals(0, personInfo.getAge());
        assertTrue(personInfo.getMedications().isEmpty());
        assertTrue(personInfo.getAllergies().isEmpty());
    }

    @Test
    void testNullValues() {

        PersonInfoDTO personInfo = new PersonInfoDTO(null, null, null, null, 0, null, null);

        assertNull(personInfo.getFirstName());
        assertNull(personInfo.getLastName());
        assertNull(personInfo.getAddress());
        assertNull(personInfo.getEmail());
        assertEquals(0, personInfo.getAge());
        assertNull(personInfo.getMedications());
        assertNull(personInfo.getAllergies());
    }

    @Test
    void testListModification() {
        List<String> medications = List.of("med1", "med2");
        List<String> allergies = List.of("allergy1", "allergy2");
        PersonInfoDTO personInfo = new PersonInfoDTO("John", "Doe", "123 Main St", "john.doe@example.com", 30, medications, allergies);

        assertThrows(UnsupportedOperationException.class, () -> personInfo.getMedications().add("med3"));
        assertThrows(UnsupportedOperationException.class, () -> personInfo.getAllergies().add("allergy3"));
    }
}
