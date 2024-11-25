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

/**
 *Controller for handling requests related to person information.
 * Provides endpoints to retrieve detailed information about persons based on their last name.
 */
@Slf4j
@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    @Autowired
    private PersonInfoService personInfoService;

    /**
     * Retrieves a list of detailed information about persons with the specified last name.
     *
     * @param lastName the last name to filter persons by.
     * @return a list of person containing detailed information about the persons with the given last name.
     */
    @GetMapping(params = "lastName")
    public List<PersonInfoDTO> getPersonsByLastName(@RequestParam String lastName) {
        log.info("Received request to get persons with last name: {}", lastName);

        List<PersonInfoDTO> response = personInfoService.getPersonsByLastName(lastName);

        log.info("Successfully fetched {} persons with last name: {}", response.size(), lastName);
        return response;
    }
}
