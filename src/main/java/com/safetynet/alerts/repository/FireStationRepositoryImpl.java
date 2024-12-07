package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.DataLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FireStationRepositoryImpl implements FireStationRepository {

    private final List<FireStation> fireStations;

    public FireStationRepositoryImpl(DataLoader dataLoader) {
        this.fireStations = new ArrayList<>(dataLoader.getData().getFirestations());
        log.info("FireStationRepository initialized with {} fire stations.", fireStations.size());
    }

    @Override
    public List<FireStation> findAll() {
        log.debug("Retrieving all fire stations.");
        try {
            log.info("Successfully retrieved {} fire stations.", fireStations.size());
            return new ArrayList<>(fireStations);
        } catch (Exception e) {
            log.error("Error occurred while retrieving fire stations.", e);
            throw e;
        }
    }

    @Override
    public Optional<FireStation> findByAddress(String address) {
        log.debug("Searching for fire station with address: {}", address);
        try {
            return fireStations.stream()
                               .filter(fireStation -> fireStation.getAddress().equals(address))
                               .findFirst();
        } catch (Exception e) {
            log.error("Error occurred while searching for fire station with address: {}", address, e);
            throw e;
        }
    }

    @Override
    public void save(FireStation fireStation) {
        log.info("Saving new fire station: {}", fireStation);
        try {
            fireStations.add(fireStation);
            log.debug("Fire station saved: {}", fireStation);
        } catch (Exception e) {
            log.error("Error occurred while saving fire station: {}", fireStation, e);
            throw e;
        }
    }

    @Override
    public void delete(FireStation fireStation) {
        log.info("Attempting to delete fire station: {}", fireStation);
        try {
            fireStations.remove(fireStation);
            log.debug("Fire station deleted: {}", fireStation);
        } catch (Exception e) {
            log.error("Error occurred while deleting fire station: {}", fireStation, e);
            throw e;
        }
    }

    @Override
    public void update(FireStation updatedFireStation) {
        log.info("Attempting to update fire station with address: {}", updatedFireStation.getAddress());
        try {
            findByAddress(updatedFireStation.getAddress())
                    .ifPresent(stationToUpdate -> {
                        stationToUpdate.setStation(updatedFireStation.getStation());
                        log.debug("Fire station updated: {}", stationToUpdate);
                    });
        } catch (Exception e) {
            log.error("Error occurred while updating fire station: {}", updatedFireStation, e);
            throw e;
        }
    }

    @Override
    public Optional<List<String>> findAddressByStationNumber(String stationNumber) {
        log.debug("Searching for addresses with station number: {}", stationNumber);
        try {
            List<String> addresses = fireStations.stream()
                                                 .filter(fireStation -> fireStation.getStation().equals(stationNumber))
                                                 .map(FireStation::getAddress)
                                                 .toList();
            if (addresses.isEmpty()) {
                log.info("No addresses found for station number: {}", stationNumber);
                return Optional.empty();
            }
            log.info("Found {} addresses for station number: {}", addresses.size(), stationNumber);
            return Optional.of(addresses);
        } catch (Exception e) {
            log.error("Error occurred while searching for addresses with station number: {}", stationNumber, e);
            throw e;
        }
    }
}
