package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;

import java.util.List;
import java.util.Optional;

public interface FireStationRepository {
    List<FireStation>findAll();
    Optional<FireStation> findByAddress(String address);
    void update(FireStation fireStation);
    void save(FireStation fireStation);
    void delete(FireStation fireStation);
    Optional<List<String>> findAddressByStationNumber(String stationNumber);
}
