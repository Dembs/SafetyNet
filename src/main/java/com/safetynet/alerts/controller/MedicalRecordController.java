package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing medical records.
 * Provides endpoints to retrieve, add, update, and delete medical records.
 */
@Slf4j
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    /**
     * Retrieves a list of all medical records in the repository.
     *
     */
    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        log.info("Received request to get all medical records.");
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        log.info("Fetched {} medical records.", medicalRecords.size());
        return medicalRecords;
    }

    /**
     * Adds a new medical record to the repository.
     *
     */
    @PostMapping
    public String addOneMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("Received request to add a new medical record: {}", medicalRecord);
        medicalRecordRepository.save(medicalRecord);
        log.info("Medical record added successfully for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        return "Medical Record added successfully";
    }

    /**
     * Deletes an existing medical record from the repository.
     *
     */
    @DeleteMapping
    public String deleteOneMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("Received request to delete medical record: {}", medicalRecord);
        medicalRecordRepository.delete(medicalRecord);
        log.info("Medical record deleted successfully for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        return "MedicalRecord deleted successfully";
    }

    /**
     * Updates an existing medical record in the repository.
     *
     */
    @PutMapping
    public String updateOneMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("Received request to update medical record: {}", medicalRecord);
        medicalRecordRepository.update(medicalRecord);
        log.info("Medical record updated successfully for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        return "MedicalRecord updated successfully";
    }
}
