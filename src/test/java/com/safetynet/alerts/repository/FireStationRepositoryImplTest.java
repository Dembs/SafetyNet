package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Data;
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

class FireStationRepositoryImplTest {

    @Mock
    private DataLoader dataLoader;

    private FireStationRepository fireStationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Data mockData = new Data();
        List<FireStation> mockFireStations = Arrays.asList(
                new FireStation("1509 Culver St", "3"),
                new FireStation("29 15th St", "2")
        );
        mockData.setFirestations(mockFireStations);

        when(dataLoader.getData()).thenReturn(mockData);

        fireStationRepository = new FireStationRepositoryImpl(dataLoader);
    }


    @Test
    void findAllTest() {
        List<FireStation> allStations = fireStationRepository.findAll();

        assertNotNull(allStations);
        assertEquals(2, allStations.size());
        assertEquals("1509 Culver St", allStations.get(0).getAddress());
        assertEquals("3", allStations.get(0).getStation());
    }

    @Test
    void saveAndFindFireStationTest() {
        FireStation newStation = new FireStation("New Address", "5");
        fireStationRepository.save(newStation);

        List<FireStation> allStations = fireStationRepository.findAll();
        Optional<FireStation> foundStation = fireStationRepository.findByAddress("New Address");

        assertEquals(3, allStations.size());
        assertTrue(foundStation.isPresent());
        assertEquals("5", foundStation.get().getStation());
    }

    @Test
    void deleteFireStationTest() {
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        fireStationRepository.delete(fireStation);

        List<FireStation> allStations = fireStationRepository.findAll();
        Optional<FireStation> foundStation = fireStationRepository.findByAddress("1509 Culver St");

        assertEquals(1, allStations.size());
        assertFalse(foundStation.isPresent());
    }

    @Test
    void updateFireStationTest() {
        FireStation updatedFireStation = new FireStation("1509 Culver St", "6");
        fireStationRepository.update(updatedFireStation);

        Optional<FireStation> retrievedStation = fireStationRepository.findByAddress("1509 Culver St");

        assertTrue(retrievedStation.isPresent());
        assertEquals("6", retrievedStation.get().getStation());
    }

    @Test
    void findAddressByStationNumberTest() {
        Optional<List<String>> addresses = fireStationRepository.findAddressByStationNumber("2");

        assertTrue(addresses.isPresent());
        assertEquals(1, addresses.get().size());
        assertEquals("29 15th St", addresses.get().get(0));
    }
}
