package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.DataLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private final List<MedicalRecord> medicalrecords;

    public MedicalRecordRepositoryImpl(DataLoader dataLoader) {
        this.medicalrecords = new ArrayList<>(dataLoader.getData().getMedicalrecords());
        log.info("MedicalRecordRepository initialized with {} records.", medicalrecords.size());
    }

    @Override
    public List<MedicalRecord> findAll() {
        log.debug("Retrieving all medical records.");
        try {
            log.info("Successfully retrieved {} medical records.", medicalrecords.size());
            return new ArrayList<>(medicalrecords);
        } catch (Exception e) {
            log.error("Error occurred while retrieving medical records.", e);
            throw e;
        }
    }

    @Override
    public Optional<MedicalRecord> findMedicalRecord(String firstName, String lastName) {
        log.debug("Searching for medical record of: {} {}", firstName, lastName);
        try {
            return medicalrecords.stream()
                                 .filter(record -> record.getFirstName().equals(firstName) &&
                                         record.getLastName().equals(lastName))
                                 .findFirst();
        } catch (Exception e) {
            log.error("Error occurred while searching for medical record: {} {}", firstName, lastName, e);
            throw e;
        }
    }

    @Override
    public void save(MedicalRecord medicalRecord) {
        log.info("Saving new medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        try {
            medicalrecords.add(medicalRecord);
            log.debug("Medical record saved: {}", medicalRecord);
        } catch (Exception e) {
            log.error("Error occurred while saving medical record: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName(), e);
            throw e;
        }
    }

    @Override
    public void delete(MedicalRecord deletedMedicalRecord) {
        log.info("Attempting to delete medical record for: {} {}", deletedMedicalRecord.getFirstName(), deletedMedicalRecord.getLastName());
        try {
            findMedicalRecord(deletedMedicalRecord.getFirstName(), deletedMedicalRecord.getLastName())
                    .ifPresent(record -> {
                        medicalrecords.remove(record);
                        log.debug("Medical record deleted: {}", record);
                    });
        } catch (Exception e) {
            log.error("Error occurred while deleting medical record for: {} {}", deletedMedicalRecord.getFirstName(), deletedMedicalRecord.getLastName(), e);
            throw e;
        }
    }

    @Override
    public void update(MedicalRecord updatedMedicalRecord) {
        log.info("Attempting to update medical record for: {} {}", updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
        try {
            findMedicalRecord(updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName())
                    .ifPresent(recordToUpdate -> {
                        recordToUpdate.setBirthdate(updatedMedicalRecord.getBirthdate());
                        recordToUpdate.setMedications(updatedMedicalRecord.getMedications());
                        recordToUpdate.setAllergies(updatedMedicalRecord.getAllergies());
                        log.debug("Medical record updated: {}", recordToUpdate);
                    });
        } catch (Exception e) {
            log.error("Error occurred while updating medical record for: {} {}", updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName(), e);
            throw e;
        }
    }
}
