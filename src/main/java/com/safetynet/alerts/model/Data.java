package com.safetynet.alerts.model;


import java.util.List;

public class Data {

    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalrecords;

    public Data(List<Person> persons, List<FireStation> firestations, List<MedicalRecord> medicalrecords) {
        this.persons = persons;
        this.firestations = firestations;
        this.medicalrecords = medicalrecords;
    }


    public List<Person> getPersons() {
        return persons;
    }


    public List<FireStation> getFirestations() {
        return firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

}