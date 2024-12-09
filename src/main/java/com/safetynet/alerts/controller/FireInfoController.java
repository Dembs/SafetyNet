package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.FireInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/fire")
public class FireInfoController {

    @Autowired
    private FireInfoService fireInfoService;

    @GetMapping(params = "address")
    public Map<String, Object> getResidentsByAddress(@RequestParam String address) {
        log.info("Received request to get residents and fire station info for address: {}", address);
        try {
            log.debug("Calling FireInfoService to fetch data for address: {}", address);
            Map<String, Object> response = fireInfoService.getResidentsByAddress(address);
            log.debug("Fetched data for address {}: {}", address, response);
            log.info("Successfully fetched residents and fire station information for address: {}", address);
            return response;
        } catch (IllegalArgumentException e) {
            log.error("Invalid address provided: {}", address, e);
            throw e;
        }
    }
}
