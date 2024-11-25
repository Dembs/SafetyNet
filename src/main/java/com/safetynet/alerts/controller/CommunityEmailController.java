package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.CommunityEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for retrieving email addresses of residents in a specific city.
 */
@Slf4j
@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    @Autowired
    private CommunityEmailService communityEmailService;

    @GetMapping(params = "city")
    public List<String> getEmailsByCity(@RequestParam String city) {
        log.info("Received request to get emails for city: {}", city);

        List<String> emails = communityEmailService.getEmailsByCity(city);

        log.info("Successfully fetched {} emails for city: {}", emails.size(), city);
        return emails;
    }
}
