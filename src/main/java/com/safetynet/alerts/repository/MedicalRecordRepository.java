package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing MedicalRecord.
 */
public interface MedicalRecordRepository {

    /**
     * Retrieves all medical records from the repository.
     *
     * @return a list of all MedicalRecord .
     */
    List<MedicalRecord> findAll();

    /**
     * Finds a medical record by the person's first name and last name.
     *
     * @param firstName the first name of the person.
     * @param lastName the last name of the person.
     * @return an Optional containing the found MedicalRecord, or empty if no match is found.
     */
    Optional<MedicalRecord> findMedicalRecord(String firstName, String lastName);

    /**
     * Updates the details of an existing medical record in the repository.
     *
     * @param medicalRecord the MedicalRecord with updated information.
     */
    void update(MedicalRecord medicalRecord);

    /**
     * Saves a new medical record to the repository.
     *
     * @param medicalRecord the MedicalRecord to be saved.
     */
    void save(MedicalRecord medicalRecord);

    /**
     * Deletes a medical record from the repository.
     *
     * @param medicalRecord the MedicalRecord to be deleted.
     */
    void delete(MedicalRecord medicalRecord);
}
