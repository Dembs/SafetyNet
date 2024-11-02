package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private List<MedicalRecord> medicalrecords = new ArrayList<>();

    @Override
    public List<MedicalRecord>findAll(){
        return medicalrecords;
    }

    @Override
    public Optional<MedicalRecord>findMedicalRecord(String firstName, String lastName){
        return medicalrecords.stream()
                             .filter(
                                     medicalRecord -> medicalRecord.getFirstName().equals(firstName) &&
                                                      medicalRecord.getLastName().equals(lastName))
                             .findFirst();
    }

    @Override
    public void save(MedicalRecord medicalRecord){
        medicalrecords.add(medicalRecord);
    }

    @Override
    public void delete(MedicalRecord medicalRecord){
        medicalrecords.remove(medicalRecord);
    }

    @Override
    public void update(MedicalRecord updatedMedicalRecord){
        findMedicalRecord(updatedMedicalRecord.getFirstName(),updatedMedicalRecord.getLastName())
                .ifPresent(
                        medicalRecordToUpdate -> {
                            medicalRecordToUpdate.setBirthdate(updatedMedicalRecord.getBirthdate());
                            medicalRecordToUpdate.setMedications(updatedMedicalRecord.getMedications());
                            medicalRecordToUpdate.setAllergies((updatedMedicalRecord.getAllergies()));
                        }
                );

    }
}