package com.safetynet.alerts.controller;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.MedicalRecordRepositoryImpl;
import com.safetynet.alerts.service.DataLoader;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordController (MedicalRecordRepository medicalRecordRepository){
        this.medicalRecordRepository = medicalRecordRepository;
    }

    // Endpoint to get all medical records
    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    //Endpoint to add one Medical Record
    @PostMapping
    public String addOneMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        medicalRecordRepository.save(medicalRecord);
        return "Medical Record added successfully";
    }
    //Endpoint to delete one Medical Record
    @DeleteMapping
    public String deleteOneMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        medicalRecordRepository.delete(medicalRecord);
        return "MedicalRecord deleted successfully";
    }

    //Endpoint to update one Medical Record
    @PutMapping
    public String updateOneMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        medicalRecordRepository.update(medicalRecord);
        return  "MedicalRecord updated successfully";
    }
}
