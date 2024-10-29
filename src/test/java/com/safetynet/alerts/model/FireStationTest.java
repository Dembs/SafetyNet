package com.safetynet.alerts.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FireStationTest {
    private static FireStation fireStation;

    @Test
    public void getFireStationTest(){
        fireStation = new FireStation("1509 Culver St","3");

        assertEquals("1509 Culver St",fireStation.getAddress());
        assertEquals("3",fireStation.getStation());
    }
}
