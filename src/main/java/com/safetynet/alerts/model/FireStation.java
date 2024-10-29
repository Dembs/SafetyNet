package com.safetynet.alerts.model;

import lombok.Data;

@Data
public class FireStation {

    private String address;
    private String station;

    public FireStation(){}

    public FireStation(String address, String station) {
        this.address = address;
        this.station = station;
    }
}
