package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.PhoneAlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller responsible for handling requests related to phone alerts.
 * Provides endpoints for retrieving phone numbers of residents covered by a specific fire station.
 */
@Slf4j
@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

    @Autowired
    private PhoneAlertService phoneAlertService;

    /**
     * Retrieves a list of phone numbers for residents covered by the specified fire station.
     *
     * @param firestation the fire station number to filter residents by.
     * @return a list of phone numbers of residents covered by the specified fire station.
     */
    @GetMapping(params = "firestation")
    public List<String> getPhoneNumbers(@RequestParam String firestation) {
        log.info("Received request to get phone numbers for fire station number: {}", firestation);

        try {
            log.debug("Calling PhoneAlertService to fetch phone numbers for fire station: {}", firestation);
            List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFireStation(firestation);
            log.debug("Phone numbers fetched: {}", phoneNumbers);
            log.info("Successfully retrieved {} phone numbers for fire station number: {}", phoneNumbers.size(), firestation);
            return phoneNumbers;
        } catch (IllegalArgumentException e) {
            log.error("Invalid fire station number provided: {}", firestation, e);
            throw e;
        }
    }
}
