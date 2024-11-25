package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ResidentInfoDTO;
import com.safetynet.alerts.service.FloodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controller for handling flood-related requests.
 * Provides an endpoint to fetch household information for a list of fire station numbers.
 */
@Slf4j
@RestController
@RequestMapping("/flood")
public class FloodController {

    @Autowired
    private FloodService floodService;

    /**
     * Fetches households covered by the specified fire stations.
     * The result includes a map where the key is the address of the household
     */
    @GetMapping("/stations")
    public Map<String, List<ResidentInfoDTO>> getHouseholdsByStations(@RequestParam List<String> stations) {
        log.info("Received request to get households for fire stations: {}", stations);

        Map<String, List<ResidentInfoDTO>> response = floodService.getHouseholdsByStations(stations);

        log.info("Successfully fetched {} households for fire stations: {}", response.size(), stations);
        return response;
    }
}
