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

@Slf4j
@RestController
@RequestMapping("/flood")
public class FloodController {

    @Autowired
    private FloodService floodService;

    @GetMapping("/stations")
    public Map<String, List<ResidentInfoDTO>> getHouseholdsByStations(@RequestParam List<String> stations) {
        log.info("Received request to get households for fire stations: {}", stations);

        try {
            log.debug("Calling FloodService to fetch households for stations: {}", stations);
            Map<String, List<ResidentInfoDTO>> response = floodService.getHouseholdsByStations(stations);
            log.debug("Fetched households: {}", response);
            log.info("Successfully fetched {} households for fire stations: {}", response.size(), stations);
            return response;
        } catch (IllegalArgumentException e) {
            log.error("Invalid fire station list provided: {}", stations, e);
            throw e;
        }
    }
}
