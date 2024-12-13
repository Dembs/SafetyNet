package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.service.FireStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/firestation")
public class FireStationController {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private FireStationService fireStationService;

    @GetMapping
    public List<FireStation> getAllFireStations() {
        log.info("Received request to fetch all fire stations.");
        try {
            List<FireStation> fireStations = fireStationRepository.findAll();
            log.debug("Fetched fire stations: {}", fireStations);
            log.info("Successfully fetched {} fire stations.", fireStations.size());
            return fireStations;
        } catch (Exception e) {
            log.error("Error occurred while fetching fire stations.", e);
            throw e;
        }
    }

    @PostMapping
    public String addOneFireStation(@RequestBody FireStation fireStation) {
        log.info("Received request to add a new fire station: {}", fireStation);
        try {
            fireStationRepository.save(fireStation);
            log.debug("Fire station saved: {}", fireStation);
            log.info("Fire station added successfully: {}", fireStation);
            return "Fire Station added successfully!";
        } catch (Exception e) {
            log.error("Error occurred while adding fire station: {}", fireStation, e);
            return "Failed to add fire station.";
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteOneFireStation(@RequestBody FireStation fireStation) {
        log.info("Received request to delete fire station: {}", fireStation);
        try {
            Optional<FireStation> existingStation = fireStationRepository.findByAddress(fireStation.getAddress());
            if (!existingStation.isPresent()) {
                log.info("Fire station not found for address: {}", fireStation.getAddress());
                return ResponseEntity.ok().body("{}");
            }
            fireStationRepository.delete(fireStation);
            log.debug("Fire station deleted: {}", fireStation);
            log.info("Fire station deleted successfully: {}", fireStation);
            return ResponseEntity.ok().body("{}");
        } catch (Exception e) {
            log.error("Error occurred while deleting fire station: {}", fireStation, e);
            return ResponseEntity.badRequest().body("Failed to delete fire station.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateOneFireStation(@RequestBody FireStation fireStation) {
        log.info("Received request to update fire station: {}", fireStation);
        try {
            Optional<FireStation> existingStation = fireStationRepository.findByAddress(fireStation.getAddress());
            if (!existingStation.isPresent()) {
                log.info("Fire station not found for address: {}", fireStation.getAddress());
                return ResponseEntity.ok().body("{}");
            }
            fireStationRepository.update(fireStation);
            log.debug("Fire station updated: {}", fireStation);
            log.info("Fire station updated successfully: {}", fireStation);
            return ResponseEntity.ok().body("{}");
        } catch (Exception e) {
            log.error("Error occurred while updating fire station: {}", fireStation, e);
            return ResponseEntity.badRequest().body("Failed to update fire station.");
        }
    }

    @GetMapping(params = "stationNumber")
    public Map<String, Object> getPersonsByStation(@RequestParam String stationNumber) {
        log.info("Received request to fetch persons covered by station number: {}", stationNumber);
        try {
            log.debug("Calling FireStationService to fetch persons for station number: {}", stationNumber);
            Map<String, Object> response = fireStationService.getPersonsByStation(stationNumber);
            log.debug("Fetched response: {}", response);
            log.info("Successfully fetched persons for station number: {}", stationNumber);
            return response;
        } catch (IllegalArgumentException e) {
            log.error("Invalid station number provided: {}", stationNumber, e);
            throw e;
        }
    }
}
