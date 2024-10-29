package com.safetynet.alerts.model;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    public MedicalRecord(){}

    public MedicalRecord(String firstName, String lastName, String birthDate, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthDate;
        this.medications = medications;
        this.allergies = allergies;
    }
}
