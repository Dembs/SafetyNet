package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository {
    List<MedicalRecord>findAll();
    Optional<MedicalRecord> findMedicalRecord(String firstName, String lastName);
    void update(MedicalRecord medicalRecord);
    void save(MedicalRecord medicalRecord);
    void delete(MedicalRecord medicalRecord);
}
