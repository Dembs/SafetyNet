package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildInfoDTO;
import com.safetynet.alerts.service.ChildAlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for retrieving information about children at a specific address.
 */
@Slf4j
@RestController
@RequestMapping("/childAlert")
public class ChildAlertController {

    @Autowired
    private ChildAlertService childAlertService;

    @GetMapping(params = "address")
    public List<ChildInfoDTO> getChildrenAtAddress(@RequestParam String address) {
        log.info("Received request to get children at address: {}", address);

        List<ChildInfoDTO> children;
        try {
            children = childAlertService.getChildrenAtAddress(address);
        } catch (Exception e) {
            log.error("An error occurred while fetching children at address: {}", address, e);
            throw e;
        }
        if (children.isEmpty()) {
            log.info("No children found at address: {}", address);
            return List.of();
        }
        log.info("Successfully retrieved {} children at address: {}", children.size(), address);
        return children;
    }
}
