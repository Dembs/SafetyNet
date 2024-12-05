package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.service.PersonInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    @Autowired
    private PersonInfoService personInfoService;

    @GetMapping(params = "lastName")
    public List<PersonInfoDTO> getPersonsByLastName(@RequestParam String lastName) {
        log.info("Received request to get persons with last name: {}", lastName);

        try {
            log.debug("Calling PersonInfoService to fetch persons with last name: {}", lastName);
            List<PersonInfoDTO> response = personInfoService.getPersonsByLastName(lastName);
            log.debug("Persons fetched: {}", response);
            log.info("Successfully fetched {} persons with last name: {}", response.size(), lastName);
            return response;
        } catch (IllegalArgumentException e) {
            log.error("Invalid last name provided: {}", lastName, e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching persons with last name: {}", lastName, e);
            throw e;
        }
    }
}
