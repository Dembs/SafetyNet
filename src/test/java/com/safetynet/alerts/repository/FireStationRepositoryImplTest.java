package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FireStationRepositoryImplTest {

    private FireStationRepository fireStationRepository;

    @BeforeEach
    public void setUp(){
        fireStationRepository = new FireStationRepositoryImpl();
    }
    @Test
    void findAll() {
    }

    @Test
    public void saveAndFindFireStationTest() {
        FireStation fireStation1 = new FireStation("1509 Culver St","3");
        fireStationRepository.save(fireStation1);
        FireStation fireStation2 = new FireStation("29 15th St","2");
        fireStationRepository.save(fireStation2);

        List<FireStation> foundAll = fireStationRepository.findAll();
        Optional<FireStation> foundStation = fireStationRepository.findByAdress("1509 Culver St");

        assertEquals(foundAll.size(),2);
        assertEquals("29 15th St",foundAll.get(1).getAddress());
        assertTrue(foundStation.isPresent());
        assertEquals("3",foundStation.get().getStation());
    }


    @Test
    void deleteFireStationTest() {
        FireStation fireStation = new FireStation("1509 Culver St","3");
        fireStationRepository.save(fireStation);
        fireStationRepository.delete(fireStation);

        Optional<FireStation> foundStation = fireStationRepository.findByAdress("1509 Culver St");

        assertFalse(foundStation.isPresent());
    }

    @Test
    void updateFireStationTest() {
        FireStation fireStation = new FireStation("1509 Culver St","3");
        fireStationRepository.save(fireStation);

        FireStation updatedFireStation = new FireStation("1509 Culver St","6");
        fireStationRepository.update(updatedFireStation);

        Optional<FireStation> retrievedStation = fireStationRepository.findByAdress("1509 Culver St");
        assertTrue(retrievedStation.isPresent());
        assertEquals("6",retrievedStation.get().getStation());
    }
}