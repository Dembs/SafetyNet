package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FireStationInfoDTOTest {

    @Test
    void testConstructorAndGetters() {
        FireStationInfoDTO fireStationInfo = new FireStationInfoDTO(
                "John",
                "Doe",
                "123 Main St",
                "123-456-7890"
        );

        assertEquals("John", fireStationInfo.getFirstName());
        assertEquals("Doe", fireStationInfo.getLastName());
        assertEquals("123 Main St", fireStationInfo.getAddress());
        assertEquals("123-456-7890", fireStationInfo.getPhone());
    }

    @Test
    void testSetters() {

        FireStationInfoDTO fireStationInfo = new FireStationInfoDTO(null, null, null, null);

        fireStationInfo.setFirstName("Jane");
        fireStationInfo.setLastName("Smith");
        fireStationInfo.setAddress("456 Elm St");
        fireStationInfo.setPhone("987-654-3210");

        assertEquals("Jane", fireStationInfo.getFirstName());
        assertEquals("Smith", fireStationInfo.getLastName());
        assertEquals("456 Elm St", fireStationInfo.getAddress());
        assertEquals("987-654-3210", fireStationInfo.getPhone());
    }

    @Test
    void testEmptyValues() {

        FireStationInfoDTO fireStationInfo = new FireStationInfoDTO("", "", null, "");

        assertEquals("", fireStationInfo.getFirstName());
        assertEquals("", fireStationInfo.getLastName());
        assertNull(fireStationInfo.getAddress());
        assertEquals("", fireStationInfo.getPhone());
    }

    @Test
    void testEquality() {

        FireStationInfoDTO fireStationInfo1 = new FireStationInfoDTO(
                "John",
                "Doe",
                "123 Main St",
                "123-456-7890"
        );
        FireStationInfoDTO fireStationInfo2 = new FireStationInfoDTO(
                "John",
                "Doe",
                "123 Main St",
                "123-456-7890"
        );

        assertEquals(fireStationInfo1.getFirstName(), fireStationInfo2.getFirstName());
        assertEquals(fireStationInfo1.getLastName(), fireStationInfo2.getLastName());
        assertEquals(fireStationInfo1.getAddress(), fireStationInfo2.getAddress());
        assertEquals(fireStationInfo1.getPhone(), fireStationInfo2.getPhone());
    }
}
