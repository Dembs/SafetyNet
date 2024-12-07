package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ResidentInfoDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FireInfoServiceIT {

    @Autowired
    private FireInfoService fireInfoService;

    @Autowired
    private DataLoader dataLoader;

    @BeforeAll
    void setupRealData() {
        dataLoader.loadData();
    }

    @Test
    void getResidentsByAddressIT() {

        String testAddress = "1509 Culver St";

        Map<String, Object> response = fireInfoService.getResidentsByAddress(testAddress);

        assertNotNull(response, "Response should not be null");

        assertTrue(response.containsKey("fireStationNumber"), "Response should contain fireStationNumber");
        assertEquals("2", response.get("fireStationNumber"), "Fire station number should be '3'");

        assertTrue(response.containsKey("residents"), "Response should contain residents");
        List<ResidentInfoDTO> residents = (List<ResidentInfoDTO>) response.get("residents");
        assertNotNull(residents, "Residents list should not be null");
        assertFalse(residents.isEmpty(), "Residents list should not be empty");

    }
}
