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
public class FloodServiceIT {

    @Autowired
    private FloodService floodService;

    @Autowired
    private DataLoader dataLoader;

    @BeforeAll
    void setupRealData() {
        dataLoader.loadData();
    }

    @Test
    void getHouseholdsByStationsIT() {
        List<String> testStationNumbers = List.of("1", "2");

        Map<String, List<ResidentInfoDTO>> response = floodService.getHouseholdsByStations(testStationNumbers);

        assertNotNull(response, "Response should not be null");
        assertFalse(response.isEmpty(), "Response should not be empty");

        assertTrue(response.size() > 0, "There should be at least one address in the response");

        String testAddress = "951 LoneTree Rd";
        assertTrue(response.containsKey(testAddress), "Response should contain the address: " + testAddress);

        List<ResidentInfoDTO> residents = response.get(testAddress);
        assertNotNull(residents, "Residents list should not be null for address: " + testAddress);
        assertFalse(residents.isEmpty(), "Residents list should not be empty for address: " + testAddress);

    }
}
