package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        log.info("Received request to get all medical records.");
        try {
            List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
            log.debug("Fetched medical records: {}", medicalRecords);
            log.info("Fetched {} medical records.", medicalRecords.size());
            return medicalRecords;
        } catch (Exception e) {
            log.error("Error occurred while fetching medical records.", e);
            throw e;
        }
    }

    @PostMapping
    public String addOneMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("Received request to add a new medical record: {}", medicalRecord);
        try {
            medicalRecordRepository.save(medicalRecord);
            log.debug("Medical record saved: {}", medicalRecord);
            log.info("Medical record added successfully for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
            return "Medical Record added successfully";
        } catch (Exception e) {
            log.error("Error occurred while adding medical record: {}", medicalRecord, e);
            return "Failed to add medical record.";
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteOneMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("Received request to delete medical record: {}", medicalRecord);
        try {
            Optional<MedicalRecord> existingRecord = medicalRecordRepository.findMedicalRecord(
                medicalRecord.getFirstName(), medicalRecord.getLastName());
            if (!existingRecord.isPresent()) {
                log.info("Medical record not found for: {} {}",
                    medicalRecord.getFirstName(), medicalRecord.getLastName());
                return ResponseEntity.ok().body("{}");
            }
            medicalRecordRepository.delete(medicalRecord);
            log.debug("Medical record deleted: {}", medicalRecord);
            log.info("Medical record deleted successfully for: {} {}", 
                medicalRecord.getFirstName(), medicalRecord.getLastName());
            return ResponseEntity.ok().body("{}");
        } catch (Exception e) {
            log.error("Error occurred while deleting medical record: {}", medicalRecord, e);
            return ResponseEntity.badRequest().body("Failed to delete medical record.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateOneMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("Received request to update medical record: {}", medicalRecord);
        try {
            Optional<MedicalRecord> existingRecord = medicalRecordRepository.findMedicalRecord(
                medicalRecord.getFirstName(), medicalRecord.getLastName());
            if (!existingRecord.isPresent()) {
                log.info("Medical record not found for: {} {}",
                    medicalRecord.getFirstName(), medicalRecord.getLastName());
                return ResponseEntity.ok().body("{}");
            }
            medicalRecordRepository.update(medicalRecord);
            log.debug("Medical record updated: {}", medicalRecord);
            log.info("Medical record updated successfully for: {} {}", 
                medicalRecord.getFirstName(), medicalRecord.getLastName());
            return ResponseEntity.ok().body("{}");
        } catch (Exception e) {
            log.error("Error occurred while updating medical record: {}", medicalRecord, e);
            return ResponseEntity.badRequest().body("Failed to update medical record.");
        }
    }
}
