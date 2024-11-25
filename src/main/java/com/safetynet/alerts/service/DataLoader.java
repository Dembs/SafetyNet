package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Data;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Service responsible for loading application data from a JSON file at startup.
 * This data includes information about persons, fire stations, and medical records.
 */
@Slf4j
@Service
public class DataLoader {

    private final ObjectMapper mapper = new ObjectMapper();
    private Data data;

    @PostConstruct
    public void loadData() {
        try {
            this.data = mapper.readValue(new File("src/main/resources/data.json"), Data.class);
            log.info("Data loaded: {} persons loaded.", data.getPersons().size());
            log.info("Data loaded: {} fire stations loaded.", data.getFirestations().size());
            log.info("Data loaded: {} medical records loaded.", data.getMedicalrecords().size());
        } catch (IOException e) {
            log.error("Error loading data from file", e);
        }
    }

    /**
     * Retrieves the loaded data.
     *
     * @return Data object containing persons, fire stations, and medical records.
     */
    public Data getData() {
        return data;
    }
}
