package com.safetynet.alerts.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PhoneAlertServiceIT {

    @Autowired
    private PhoneAlertService phoneAlertService;

    @Autowired
    private DataLoader dataLoader;

    @BeforeAll
    void setupRealData() {
        dataLoader.loadData();
    }

    @Test
    void getPhoneNumbersByFireStationIT() {

        String testFireStationNumber = "1";

        List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFireStation(testFireStationNumber);

        assertNotNull(phoneNumbers, "Phone numbers list should not be null");
        assertFalse(phoneNumbers.isEmpty(), "Phone numbers list should not be empty");

        assertEquals(6, phoneNumbers.size(), "Unexpected number of phone numbers for fire station 1");

    }

}
