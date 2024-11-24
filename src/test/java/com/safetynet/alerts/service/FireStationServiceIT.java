package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationInfoDTO;
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
public class FireStationServiceIT {

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private DataLoader dataLoader;

    @BeforeAll
    void setupRealData() {
        dataLoader.loadData();
    }

    @Test
    void getPersonsByStationIT() {

        String stationNumber = "1";

        Map<String, Object> response = fireStationService.getPersonsByStation(stationNumber);

        assertNotNull(response, "Response should not be null");

        assertTrue(response.containsKey("adults"), "Response should contain 'adults'");
        assertTrue(response.containsKey("children"), "Response should contain 'children'");
        assertTrue((int) response.get("adults") > 0, "There should be at least one adult");
        assertTrue((int) response.get("children") > 0, "There should be at least one child");

        assertTrue(response.containsKey("persons"), "Response should contain 'persons'");
        List<FireStationInfoDTO> persons = (List<FireStationInfoDTO>) response.get("persons");
        assertNotNull(persons, "Persons list should not be null");
        assertFalse(persons.isEmpty(), "Persons list should not be empty");

    }
}
