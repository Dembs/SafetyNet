package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.PhoneAlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

    @Autowired
    private PhoneAlertService phoneAlertService;

    @GetMapping(params = "firestation")
    public List<String> getPhoneNumbers(@RequestParam String firestation) {
        log.info("Received request to fetch phone numbers for fire station number: {}", firestation);

        List<String> phoneNumbers;
        try {
            phoneNumbers = phoneAlertService.getPhoneNumbersByFireStation(firestation);
            log.info("Successfully retrieved {} phone numbers for fire station number: {}", phoneNumbers.size(), firestation);
        } catch (IllegalArgumentException e) {
            log.error("Error fetching phone numbers for fire station number: {}", firestation, e);
            throw e;
        }

        return phoneNumbers;
    }
}
