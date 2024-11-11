package com.safetynet.alerts.model;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Person> persons = new ArrayList<>();
    private List<FireStation> firestations =new ArrayList<>();
    private List<MedicalRecord> medicalrecords=new ArrayList<>();

    // No-argument constructor
    public Data() {
    }

    public Data(List<Person> persons, List<FireStation> firestations, List<MedicalRecord> medicalRecords) {
        this.persons = persons;
        this.firestations = firestations;
        this.medicalrecords = medicalRecords;
    }

    // Getters and Setters
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<FireStation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<FireStation> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }
}
