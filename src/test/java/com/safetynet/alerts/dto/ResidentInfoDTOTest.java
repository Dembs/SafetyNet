package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResidentInfoDTOTest {

    @Test
    void testConstructorAndGetters() {
        ResidentInfoDTO residentInfo = new ResidentInfoDTO(
                "John",
                "Doe",
                "123-456-7890",
                40,
                List.of("med1", "med2"),
                List.of("allergy1", "allergy2")
        );

        assertEquals("John", residentInfo.getFirstName());
        assertEquals("Doe", residentInfo.getLastName());
        assertEquals("123-456-7890", residentInfo.getPhone());
        assertEquals(40, residentInfo.getAge());
        assertEquals(2, residentInfo.getMedications().size());
        assertEquals("med1", residentInfo.getMedications().get(0));
        assertEquals("med2", residentInfo.getMedications().get(1));
        assertEquals(2, residentInfo.getAllergies().size());
        assertEquals("allergy1", residentInfo.getAllergies().get(0));
        assertEquals("allergy2", residentInfo.getAllergies().get(1));
    }

    @Test
    void testSetters() {

        ResidentInfoDTO residentInfo = new ResidentInfoDTO(null, null, null, 0, null, null);

        residentInfo.setFirstName("Jane");
        residentInfo.setLastName("Smith");
        residentInfo.setPhone("987-654-3210");
        residentInfo.setAge(25);
        residentInfo.setMedications(List.of("medA", "medB"));
        residentInfo.setAllergies(List.of("allergyX"));

        assertEquals("Jane", residentInfo.getFirstName());
        assertEquals("Smith", residentInfo.getLastName());
        assertEquals("987-654-3210", residentInfo.getPhone());
        assertEquals(25, residentInfo.getAge());
        assertEquals(2, residentInfo.getMedications().size());
        assertEquals("medA", residentInfo.getMedications().get(0));
        assertEquals("medB", residentInfo.getMedications().get(1));
        assertEquals(1, residentInfo.getAllergies().size());
        assertEquals("allergyX", residentInfo.getAllergies().get(0));
    }

    @Test
    void testEmptyValues() {

        ResidentInfoDTO residentInfo = new ResidentInfoDTO("", "", "", 0, List.of(), List.of());

        assertEquals("", residentInfo.getFirstName());
        assertEquals("", residentInfo.getLastName());
        assertEquals("", residentInfo.getPhone());
        assertEquals(0, residentInfo.getAge());
        assertTrue(residentInfo.getMedications().isEmpty());
        assertTrue(residentInfo.getAllergies().isEmpty());
    }

    @Test
    void testNullValues() {
        ResidentInfoDTO residentInfo = new ResidentInfoDTO(null, null, null, 0, null, null);

        assertNull(residentInfo.getFirstName());
        assertNull(residentInfo.getLastName());
        assertNull(residentInfo.getPhone());
        assertEquals(0, residentInfo.getAge());
        assertNull(residentInfo.getMedications());
        assertNull(residentInfo.getAllergies());
    }

    @Test
    void testListModification() {
        List<String> medications = List.of("med1", "med2");
        List<String> allergies = List.of("allergy1", "allergy2");
        ResidentInfoDTO residentInfo = new ResidentInfoDTO("John", "Doe", "123-456-7890", 40, medications, allergies);

        assertThrows(UnsupportedOperationException.class, () -> residentInfo.getMedications().add("med3"));
        assertThrows(UnsupportedOperationException.class, () -> residentInfo.getAllergies().add("allergy3"));
    }
}
