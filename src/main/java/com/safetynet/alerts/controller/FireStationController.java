package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.service.FireStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for managing fire station operations.
 * Provides endpoints to retrieve, add, update, and delete fire stations.
 */
@Slf4j
@RestController
@RequestMapping("/firestation")
public class FireStationController {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private FireStationService fireStationService;

    /**
     * Fetches a list of all fire stations.
     *
     */
    @GetMapping
    public List<FireStation> getAllFireStations() {
        log.info("Fetching all fire stations");
        return fireStationRepository.findAll();
    }

    /**
     * Adds a new fire station.
     *
     */
    @PostMapping
    public String addOneFireStation(@RequestBody FireStation fireStation) {
        log.info("Adding new fire station: {}", fireStation);
        fireStationRepository.save(fireStation);
        log.info("Fire station added successfully: {}", fireStation);
        return "Fire Station added successfully!";
    }

    /**
     * Deletes an existing fire station.
     *
     */
    @DeleteMapping
    public String deleteOneFireSation(@RequestBody FireStation fireStation) {
        log.info("Deleting fire station: {}", fireStation);
        fireStationRepository.delete(fireStation);
        log.info("Fire station deleted successfully: {}", fireStation);
        return "Fire Station deleted successfully";
    }

    /**
     * Updates an existing fire station.
     *
     */
    @PutMapping
    public String updateOnePerson(@RequestBody FireStation fireStation) {
        log.info("Updating fire station: {}", fireStation);
        fireStationRepository.update(fireStation);
        log.info("Fire station updated successfully: {}", fireStation);
        return "Fire Station updated successfully";
    }

    /**
     * Fetches a list of persons covered by a specific fire station number.
     * Includes additional information such as the number of adults and children covered by the station.
     *
     */
    @GetMapping(params = "stationNumber")
    public Map<String, Object> getPersonsByStation(@RequestParam String stationNumber) {
        log.info("Fetching persons covered by station number: {}", stationNumber);
        try {
            Map<String, Object> response = fireStationService.getPersonsByStation(stationNumber);
            log.info("Successfully fetched persons for station number: {}", stationNumber);
            return response;
        } catch (IllegalArgumentException e) {
            log.error("Error fetching persons for station number: {}", stationNumber, e);
            throw e;
        }
    }
}
