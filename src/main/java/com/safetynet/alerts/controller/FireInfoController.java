package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.FireInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fire")
public class FireInfoController {

    @Autowired
    private FireInfoService fireInfoService;

    @GetMapping(params = "address")
    public Map<String, Object> getResidentsByAddress(@RequestParam String address) {
        return fireInfoService.getResidentsByAddress(address);
    }
}
