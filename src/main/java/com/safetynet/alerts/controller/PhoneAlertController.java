package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.PhoneAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

    @Autowired
    private PhoneAlertService phoneAlertService;

    @GetMapping(params = "firestation")
    public List<String> getPhoneNumbers(@RequestParam String firestation) {
        return phoneAlertService.getPhoneNumbersByFireStation(firestation);
    }
}
