package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonInfoDTOTest {

    @Test
    void gettersTest() {

        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String phone = "555-1234";

        PersonInfoDTO personInfo = new PersonInfoDTO(firstName, lastName, address, phone);

        assertEquals(firstName, personInfo.getFirstName());
        assertEquals(lastName, personInfo.getLastName());
        assertEquals(address, personInfo.getAddress());
        assertEquals(phone, personInfo.getPhone());
    }

    @Test
    void settersTest() {
        PersonInfoDTO personInfo = new PersonInfoDTO("", "", "", "");

        personInfo.setFirstName("Jane");
        personInfo.setLastName("Smith");
        personInfo.setAddress("456 Elm St");
        personInfo.setPhone("555-5678");

        assertEquals("Jane", personInfo.getFirstName());
        assertEquals("Smith", personInfo.getLastName());
        assertEquals("456 Elm St", personInfo.getAddress());
        assertEquals("555-5678", personInfo.getPhone());
    }

}
